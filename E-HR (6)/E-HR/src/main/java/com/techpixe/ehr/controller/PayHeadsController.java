package com.techpixe.ehr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techpixe.ehr.entity.PayHeads;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.repository.UserRepository;
import com.techpixe.ehr.service.PayHeadsService;

import java.util.List;

@RestController
@RequestMapping("/api/payheads")
public class PayHeadsController {

	@Autowired
	private PayHeadsService payHeadsService;
	@Autowired
	private UserRepository userRepository;

	// Create a new PayHead
	@PostMapping("/user/{userId}")
	public ResponseEntity<PayHeads> createPayHead(@PathVariable Long userId, @RequestBody PayHeads payHead) {
		User user_Id = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(userId + " is not found"));
		payHead.setUser(user_Id);

		PayHeads createdPayHead = payHeadsService.createPayHead(payHead);
		return ResponseEntity.ok(createdPayHead);
	}

	// Get all PayHeads
	@GetMapping
	public List<PayHeads> getAllPayHeads() {
		return payHeadsService.getAllPayHeads();
	}

	// Get a PayHead by ID
	@GetMapping("/{payHeadId}")
	public ResponseEntity<PayHeads> getPayHeadById(@PathVariable Long payHeadId) {
		return payHeadsService.getPayHeadById(payHeadId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// Update a PayHead
	@PutMapping("/{payHeadId}")
	public ResponseEntity<PayHeads> updatePayHead(@PathVariable Long payHeadId, @RequestParam String payHeadName,
			@RequestParam String payHeadDescription, @RequestParam String payHeadType) {

		PayHeads updatedPayHead = payHeadsService.updatePayHead(payHeadId, payHeadName, payHeadDescription,
				payHeadType);
		return ResponseEntity.ok(updatedPayHead);
	}

	// Delete a PayHead
	@DeleteMapping("/{payHeadId}")
	public ResponseEntity<Void> deletePayHead(@PathVariable Long payHeadId) {
		payHeadsService.deletePayHead(payHeadId);
		return ResponseEntity.noContent().build();
	}
}
