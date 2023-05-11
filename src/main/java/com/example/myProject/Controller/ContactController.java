package com.example.myProject.Controller;

import com.example.myProject.Model.Contact;
import com.example.myProject.Repository.ContactRepository;
import com.example.myProject.Service.ContactService;

import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/contact")
@ApiOperation(value = "User Controller")
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactService contactService;

	@GetMapping("/listContact")
	public Map<String, Object> listAllContact() {
		Map<String, Object> data = new HashedMap<String, Object>();
		List<Contact> contacts = new ArrayList<Contact>();
		try {
			contacts = contactRepository.findAll();
			data.put("contactList", contacts);
			data.put("Result", "Success");
		} catch (Exception e) {
			data.put("contactList", 0);
			data.put("Result", "Failed");
		}
		return data;
	}

	@GetMapping("/contactDetails")
	public Map<String, Object> getContactDetails(@RequestParam(value = "contactId") long contactId) {
		Map<String, Object> data = new HashedMap<String, Object>();
		Optional<Contact> contact = Optional.of(new Contact());
		try {
			contact = contactRepository.findById(contactId);

		} catch (Exception e) {
			data.put("contactDetails", "No information found");
			data.put("Result", "Failed");
			return data;
		}
		if (contact.isPresent()) {
			data.put("contactDetails", contact);
			data.put("Result", "Success");
			return data;
		} else {
			data.put("contactDetails", "No information found! invalid contactId");
			data.put("Result", "Failed");
			return data;
		}
	}

	@PostMapping("/addContact")
	public Map<String, Object> addContact(@RequestBody Contact user) {
		Map<String, Object> data = new HashedMap<String, Object>();
		try {
			contactRepository.save(user);
		} catch (Exception e) {
			data.put("contactAdded", false);
			return data;
		}
		data.put("contactAdded", true);
		return data;
	}

	@PutMapping("/updateContact")
	public Map<String, Object> updateContact(@RequestBody Contact user) {
		return contactService.updateContact(user);
	}

	@GetMapping("/findContact")
	public Map<String, Object> searchContact(@RequestParam(value = "key") String key) {
		Map<String, Object> data = new HashedMap<String, Object>();
		List<Contact> cont = new ArrayList<Contact>();
		try {
			cont = contactRepository.findByKey(key);
		} catch (Exception e) {
			data.put("contactList", 0);
			data.put("Result", "Failed");
			return data;
		}
		data.put("contactList", cont);
		data.put("Result", "Success");
		return data;
	}

	@DeleteMapping("/deleteContact")
	public Map<String, Object> deleteContact(@RequestParam(value = "contactId") long contactId) {
		Map<String, Object> data = new HashedMap<String, Object>();

		try {
			contactRepository.deleteById(contactId);
		} catch (Exception e) {
			data.put("Result", "Failed");
			data.put("contactDeleted", false);
			return data;
		}
		data.put("Result", "Success");
		data.put("contactDeleted", true);
		return data;
	}

}
