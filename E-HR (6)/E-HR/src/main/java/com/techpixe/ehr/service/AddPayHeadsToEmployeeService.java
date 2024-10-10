package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.AddPayHeadsToEmployee;

public interface AddPayHeadsToEmployeeService {
	public AddPayHeadsToEmployee createAddPayHeadsToEmployee(AddPayHeadsToEmployee addPayHeadsToEmployee);

	public List<AddPayHeadsToEmployee> getAllAddPayHeadsToEmployee();

	public Optional<AddPayHeadsToEmployee> getAddPayHeadsToEmployeeById(Long id);

	public AddPayHeadsToEmployee updateAddPayHeadsToEmployee(Long id,
			AddPayHeadsToEmployee addPayHeadsToEmployeeDetails);

	public void deleteAddPayHeadsToEmployee(Long id);

}
