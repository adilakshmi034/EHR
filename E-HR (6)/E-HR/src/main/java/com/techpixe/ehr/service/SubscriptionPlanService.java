package com.techpixe.ehr.service;

import java.util.List;

import com.techpixe.ehr.entity.SubscriptionPlan;


public interface SubscriptionPlanService {

	SubscriptionPlan createSubscriptionPlan(SubscriptionPlan subscriptionPlan);

	SubscriptionPlan updateSubscriptionPlan(Long id, SubscriptionPlan subscriptionPlan);

	SubscriptionPlan getSubscriptionPlanById(Long id);

	List<SubscriptionPlan> getAllSubscriptionPlans();

	void deleteSubscriptionPlan(Long id);
	

}
