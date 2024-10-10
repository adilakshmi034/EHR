package com.techpixe.ehr.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techpixe.ehr.entity.AddPayHeadsToEmployee;
import com.techpixe.ehr.entity.LeaveapprovalTable;
import com.techpixe.ehr.repository.LeaveapprovalTableRepository;
import com.techpixe.ehr.service.LeaveapprovalTableService;

@Service
public class LeaveapprovalTableServiceImpl implements LeaveapprovalTableService {

	@Autowired
	private LeaveapprovalTableRepository leaveapprovalTableRepository;

	// Create
	@Override
	public LeaveapprovalTable createLeaveapprovalTable(LeaveapprovalTable leaveapprovalTable) {
		return leaveapprovalTableRepository.save(leaveapprovalTable);
	}

	@Override
	public List<LeaveapprovalTable> getAllLeaveapprovalTables() {
		return leaveapprovalTableRepository.findAll();
	}

	// Read by ID
	@Override

	public Optional<LeaveapprovalTable> getLeaveapprovalTableById(Long id) {
		return leaveapprovalTableRepository.findById(id);
	}

	// Update
	@Override

	public LeaveapprovalTable updateLeaveapprovalTable(Long id, LeaveapprovalTable leaveapprovalTableDetails) {
		LeaveapprovalTable leaveapprovalTable = leaveapprovalTableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("LeaveapprovalTable not found"));

		leaveapprovalTable.setSubject(leaveapprovalTableDetails.getSubject());
		leaveapprovalTable.setStartdate(leaveapprovalTableDetails.getStartdate());
		leaveapprovalTable.setEnddate(leaveapprovalTableDetails.getEnddate());
		leaveapprovalTable.setMessage(leaveapprovalTableDetails.getMessage());
		leaveapprovalTable.setType(leaveapprovalTableDetails.getType());
		leaveapprovalTable.setStatus(leaveapprovalTableDetails.getStatus());
		leaveapprovalTable.setEmployeeTable(leaveapprovalTableDetails.getEmployeeTable());

		return leaveapprovalTableRepository.save(leaveapprovalTable);
	}

	// Delete
	@Override

	public void deleteLeaveapprovalTable(Long id) {
		leaveapprovalTableRepository.deleteById(id);
	}

	@Transactional
	@Override
	public LeaveapprovalTable updateLeaveApprovalStatus(Long id, String status) {
		LeaveapprovalTable leaveApproval = leaveapprovalTableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Leave Approval not found"));

		// Check if start date is over
		if (leaveApproval.getStartdate().isBefore(LocalDate.now())) {
			throw new RuntimeException("Cannot update status as the start date has passed");
		}

		// Update status
		leaveApproval.setStatus(status);
		return leaveapprovalTableRepository.save(leaveApproval);
	}
}
