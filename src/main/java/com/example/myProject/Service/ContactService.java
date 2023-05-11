package com.example.myProject.Service;

import com.example.myProject.Model.Contact;
import com.example.myProject.Repository.ContactRepository;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository userRepository;

    public Contact findByUserName(String userName) {
        return userRepository.findByFirstName(userName);
    }

	public Map<String, Object> updateContact(Contact update) {
		Map<String,Object> data = new HashedMap<String, Object>();
		
		Contact con = new Contact();
			
		try {
			con = userRepository.findByFirstName(update.getFirstName());
		} catch (Exception e) {
			data.put("Result", "Faild to get contact information"+e);
			return data;
			
		}
		
		if(con != null) {
			con.setEmail(update.getEmail());
			con.setFirstName(update.getFirstName());
			con.setLastName(update.getLastName());
			con.setPhone(update.getPhone());
			userRepository.save(con);
			data.put("Result", "contact updated successfully");
			return data;
		}
		data.put("Result", "No contact details found");
		return data;
	}
}
