package com.techpixe.ehr.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.ehr.entity.Attedence;
import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.repository.EmployeeTableRepository;
import com.techpixe.ehr.service.AttedenceService;

@RestController
@RequestMapping("/api/attedence")
public class AttedenceController {

	@Autowired
	private AttedenceService attedenceService;
	@Autowired
	private EmployeeTableRepository employeeTableRepository;

	// Create a new Attedence
	@PostMapping("/employee/{empId}")
	public ResponseEntity<Attedence> createAttedence(@PathVariable Long empId, @RequestBody Attedence attedence) {

		EmployeeTable employeeTable = employeeTableRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException(empId + "is not present"));
		attedence.setEmployeeTable(employeeTable);
		attedence.setName(employeeTable.getFullName());
		attedence.setEmpCode(employeeTable.getEmpCode());
		attedence.setAttendence(true);
		Attedence createdAttedence = attedenceService.createAttedence(attedence);
		return ResponseEntity.ok(createdAttedence);
	}

	@GetMapping
	public List<Attedence> getAllAttedences() {
		return attedenceService.getAllAttedences();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Attedence> getAttedenceById(@PathVariable Long id) {
		return attedenceService.getAttedenceById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Attedence> updateAttedence(@PathVariable Long id, @RequestBody Attedence attedenceDetails) {
		Attedence updatedAttedence = attedenceService.updateAttedence(id, attedenceDetails);
		return ResponseEntity.ok(updatedAttedence);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAttedence(@PathVariable Long id) {
		attedenceService.deleteAttedence(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/employee/{empId}/attendance/{date}")
	public ResponseEntity<Attedence> getAttendanceByDate(@PathVariable Long empId, @PathVariable LocalDate date) {
		Attedence attendance = attedenceService.getAttendanceByDate(empId, date);
		return ResponseEntity.ok(attendance);
	}
}
