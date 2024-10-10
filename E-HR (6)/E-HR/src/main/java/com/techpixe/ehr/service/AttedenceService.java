package com.techpixe.ehr.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.Attedence;

public interface AttedenceService {
	public Attedence createAttedence(Attedence attedence);

	public List<Attedence> getAllAttedences();

	public Optional<Attedence> getAttedenceById(Long id);

	public Attedence updateAttedence(Long id, Attedence attedenceDetails);

	public void deleteAttedence(Long id);

	public Attedence getAttendanceByDate(Long empId, LocalDate date);

}
