package com.techpixe.ehr.serviceimpl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.ehr.entity.Attedence;
import com.techpixe.ehr.repository.AttedenceRepository;
import com.techpixe.ehr.service.AttedenceService;

@Service
public class AttedenceServiceImpl implements AttedenceService {

	@Autowired
	private AttedenceRepository attedenceRepository;

	// Create
	public Attedence createAttedence(Attedence attedence) {

		return attedenceRepository.save(attedence);
	}

	// Read
	public List<Attedence> getAllAttedences() {
		return attedenceRepository.findAll();
	}

	public Optional<Attedence> getAttedenceById(Long id) {
		return attedenceRepository.findById(id);
	}

	// Update
	public Attedence updateAttedence(Long id, Attedence attedenceDetails) {
		Attedence attedence = attedenceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Attedence not found"));

		// Update fields
		attedence.setDate(attedenceDetails.getDate());
		attedence.setName(attedenceDetails.getName());
		attedence.setPunchIn(attedenceDetails.getPunchIn());
		attedence.setPunchOut(attedenceDetails.getPunchOut());
		attedence.setPunchInMessage(attedenceDetails.getPunchInMessage());
		attedence.setPunchOutMessage(attedenceDetails.getPunchOutMessage());
		Duration duration = Duration.between(attedenceDetails.getPunchIn(), attedenceDetails.getPunchOut());
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		String formattedWorkingHours = String.format("%02d:%02d", hours, minutes);
		attedence.setWorkingHours(formattedWorkingHours);
		attedence.setEmpCode(attedenceDetails.getEmpCode());
		attedence.setEmployeeTable(attedence.getEmployeeTable());

		return attedenceRepository.save(attedence);
	}

	// Delete
	public void deleteAttedence(Long id) {
		attedenceRepository.deleteById(id);
	}

	@Override
	public Attedence getAttendanceByDate(Long empId, LocalDate date) {
		return attedenceRepository.findByEmployeeTableIdAndDate(empId, date);
	}

}
