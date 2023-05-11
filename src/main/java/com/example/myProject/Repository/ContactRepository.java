package com.example.myProject.Repository;

import com.example.myProject.Model.Contact;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Contact findByFirstName(String userName);

	@Query(nativeQuery = true,value = "SELECT * FROM contacts WHERE first_name LIKE %:key% OR last_name LIKE %:key% OR email  LIKE %:key%")
	List<Contact> findByKey(@Param("key") String key);

}
