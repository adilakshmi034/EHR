package com.techpixe.ehr.service;

import java.util.List;
import java.util.Optional;

import com.techpixe.ehr.entity.PayHeads;

public interface PayHeadsService {
	public PayHeads createPayHead(PayHeads payHead);

	public List<PayHeads> getAllPayHeads();

	public Optional<PayHeads> getPayHeadById(Long payHeadId);

	public PayHeads updatePayHead(Long payHeadId, String payHeadName, String payHeadDescription, String payHeadType);

	public void deletePayHead(Long payHeadId);

}
