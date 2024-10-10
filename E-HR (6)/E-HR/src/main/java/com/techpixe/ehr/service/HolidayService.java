package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.Holiday;

public interface HolidayService {
	public Holiday createHoliday(Holiday holiday);

	public List<Holiday> getAllHolidays();

	public Optional<Holiday> getHolidayById(Long holidayId);

	public Holiday updateHoliday(Long holidayId, String holidayTitle, String description, String holidayDate,
			String holidayType);
	public void deleteHoliday(Long holidayId);

}
