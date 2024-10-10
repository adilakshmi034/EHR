package com.techpixe.ehr.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.ehr.dto.AddJobDetailsDto;
import com.techpixe.ehr.entity.AddJobDetails;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.repository.AddJobDetailsRepository;
import com.techpixe.ehr.repository.UserRepository;
import com.techpixe.ehr.service.AddJobDetailsService;

@Service
public class AddJobDetailsServiceImpl implements AddJobDetailsService {

	@Autowired
	private AddJobDetailsRepository addJobDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public AddJobDetails saveJobDetails(AddJobDetails jobDetails, Long user_Id) {
		User userId = userRepository.findById(user_Id)
				.orElseThrow(() -> new RuntimeException(user_Id + " is not found"));
		jobDetails.setCreatedAt(LocalDate.now());
		jobDetails.setUser(userId);
		return addJobDetailsRepository.save(jobDetails);
	}

	@Override
	public Optional<AddJobDetails> getJobDetailsById(Long jobId) {
		return addJobDetailsRepository.findById(jobId);
	}

	@Override
	public List<AddJobDetails> getAllJobDetails() {
		return addJobDetailsRepository.findAll();
	}

	@Override
	public void deleteJobDetails(Long jobId) {
		addJobDetailsRepository.deleteById(jobId);
	}

	@Override
	public AddJobDetails updateJobDetails(Long jobId, AddJobDetailsDto updateDto) {
		AddJobDetails existingJobDetails = addJobDetailsRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found"));

		existingJobDetails.setJobTitle(updateDto.getJobTitle());
		existingJobDetails.setJobkeyskills(updateDto.getJobkeyskills());
		existingJobDetails.setCreatedAt(updateDto.getCreatedAt());
		existingJobDetails.setYearsOfExperience(updateDto.getYearsOfExperience());
		existingJobDetails.setNoOfVacancies(updateDto.getNoOfVacancies());
		String formattedPercentage = updateDto.getOverallPercentage() + "%";
		existingJobDetails.setOverallPercentage(formattedPercentage);

		return addJobDetailsRepository.save(existingJobDetails);
	}

}