package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.dto.AddJobDetailsDto;
import com.techpixe.ehr.entity.AddJobDetails;

public interface AddJobDetailsService {
	AddJobDetails saveJobDetails(AddJobDetails jobDetails,Long user_Id);

	Optional<AddJobDetails> getJobDetailsById(Long jobId);

	List<AddJobDetails> getAllJobDetails();

	void deleteJobDetails(Long jobId);
	public AddJobDetails updateJobDetails(Long jobId, AddJobDetailsDto updateDto) ;


}