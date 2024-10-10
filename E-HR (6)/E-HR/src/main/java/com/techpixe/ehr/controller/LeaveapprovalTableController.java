package com.techpixe.ehr.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.LeaveapprovalTable;
import com.techpixe.ehr.repository.EmployeeTableRepository;
import com.techpixe.ehr.service.LeaveapprovalTableService;

@RestController
@RequestMapping("/api/leaveapproval")
public class LeaveapprovalTableController {
	@Autowired
	private LeaveapprovalTableService leaveapprovalTableService;

	@Autowired

	private EmployeeTableRepository employeeTableRepository;

	@PostMapping("/employee/{empId}")
	public ResponseEntity<LeaveapprovalTable> createLeaveapprovalTable(@PathVariable Long empId,
			@RequestBody LeaveapprovalTable leaveapprovalTable) {
		EmployeeTable employeeTable = employeeTableRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException(empId + "is not present"));
		leaveapprovalTable.setEmployeeTable(employeeTable);

		LeaveapprovalTable createdLeaveapprovalTable = leaveapprovalTableService
				.createLeaveapprovalTable(leaveapprovalTable);
		return ResponseEntity.ok(createdLeaveapprovalTable);
	}

	// Get all LeaveapprovalTables
	@GetMapping
	public List<LeaveapprovalTable> getAllLeaveapprovalTables() {
		return leaveapprovalTableService.getAllLeaveapprovalTables();
	}

	// Get a LeaveapprovalTable by ID
	@GetMapping("/{id}")
	public ResponseEntity<LeaveapprovalTable> getLeaveapprovalTableById(@PathVariable Long id) {
		return leaveapprovalTableService.getLeaveapprovalTableById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// Update a LeaveapprovalTable
	@PutMapping("/{id}")
	public ResponseEntity<LeaveapprovalTable> updateLeaveapprovalTable(@PathVariable Long id,
			@RequestBody LeaveapprovalTable leaveapprovalTableDetails) {
		LeaveapprovalTable updatedLeaveapprovalTable = leaveapprovalTableService.updateLeaveapprovalTable(id,
				leaveapprovalTableDetails);
		return ResponseEntity.ok(updatedLeaveapprovalTable);
	}

	// Delete a LeaveapprovalTable
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLeaveapprovalTable(@PathVariable Long id) {
		leaveapprovalTableService.deleteLeaveapprovalTable(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<LeaveapprovalTable> updateStatus(@PathVariable Long id, @RequestParam String status) {
		LeaveapprovalTable updatedLeaveApproval = leaveapprovalTableService.updateLeaveApprovalStatus(id, status);
		return ResponseEntity.ok(updatedLeaveApproval);
	}
}
