package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.LeaveapprovalTable;

public interface LeaveapprovalTableService {
	public LeaveapprovalTable createLeaveapprovalTable(LeaveapprovalTable leaveapprovalTable);

	public List<LeaveapprovalTable> getAllLeaveapprovalTables();

	public Optional<LeaveapprovalTable> getLeaveapprovalTableById(Long id);

	public LeaveapprovalTable updateLeaveapprovalTable(Long id, LeaveapprovalTable leaveapprovalTableDetails);

	public void deleteLeaveapprovalTable(Long id);
	public LeaveapprovalTable updateLeaveApprovalStatus(Long id, String status) ;


}
