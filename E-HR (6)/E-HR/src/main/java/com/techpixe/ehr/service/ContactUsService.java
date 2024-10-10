package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.ContactUs;

public interface ContactUsService {
	public ContactUs saveContactUs(ContactUs contactUs);

	public Optional<ContactUs> getContactUsById(Long id);

	public List<ContactUs> getAllContactUss();

	public void deleteContactUs(Long id);

	public ContactUs updateContactUs(Long id, ContactUs updatedContactUs);

}
