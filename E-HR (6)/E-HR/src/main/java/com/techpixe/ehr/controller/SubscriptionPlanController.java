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

import com.techpixe.ehr.dto.ErrorResponseDto;
import com.techpixe.ehr.entity.SubscriptionPlan;
import com.techpixe.ehr.service.AdminService;
import com.techpixe.ehr.service.SubscriptionPlanService;

@RequestMapping("/api/subscriptions")
@RestController
public class SubscriptionPlanController {

	@Autowired
	private SubscriptionPlanService subscriptionPlanService;
	
	@Autowired
	private AdminService adminService;

	
	
	  @PostMapping("/save")
	    public ResponseEntity<SubscriptionPlan> createSubscriptionPlan(@RequestBody SubscriptionPlan subscriptionPlan) {
	        SubscriptionPlan createdPlan = subscriptionPlanService.createSubscriptionPlan(subscriptionPlan);
	        return ResponseEntity.ok(createdPlan);
	    }

	    @PutMapping("/edit/{id}")
	    public ResponseEntity<SubscriptionPlan> updateSubscriptionPlan(
	            @PathVariable Long id,
	            @RequestBody SubscriptionPlan subscriptionPlan) {
	        SubscriptionPlan updatedPlan = subscriptionPlanService.updateSubscriptionPlan(id, subscriptionPlan);
	        if (updatedPlan != null) {
	            return ResponseEntity.ok(updatedPlan);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @GetMapping("/get/{id}")
	    public ResponseEntity<SubscriptionPlan> getSubscriptionPlanById(@PathVariable Long id) {
	        SubscriptionPlan plan = subscriptionPlanService.getSubscriptionPlanById(id);
	        if (plan != null) {
	            return ResponseEntity.ok(plan);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
	        subscriptionPlanService.deleteSubscriptionPlan(id);
	        return ResponseEntity.noContent().build();
	    }

	    @GetMapping("/getAll")
	    public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans() {
	        List<SubscriptionPlan> plans = subscriptionPlanService.getAllSubscriptionPlans();
	        return ResponseEntity.ok(plans);
	    }
	
	

		@PostMapping("/login")
		public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, @RequestParam String password) {
			if (emailOrMobileNumber != null) {
				if (isEmail(emailOrMobileNumber)) {
					return adminService.loginByEmail(emailOrMobileNumber, password);
				} else if (isMobileNumber(emailOrMobileNumber)) {
					return adminService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
				} else {
					ErrorResponseDto errorResponse = new ErrorResponseDto();
					errorResponse
							.setError("Invalid emailOrMobileNumber format. Please provide a valid email or mobile number.");
					return ResponseEntity.internalServerError().body(errorResponse);
				}
			} else {
				ErrorResponseDto errorResponse = new ErrorResponseDto();
				errorResponse.setError("Invalid input. Email or mobile number must be provided.");
				return ResponseEntity.internalServerError().body(errorResponse);
			}

		}


		private boolean isEmail(String emailOrMobileNumber) {
			return emailOrMobileNumber.contains("@");
		}

		private boolean isMobileNumber(String emailOrMobileNumber) {
			return emailOrMobileNumber.matches("\\d+");
		}

}
