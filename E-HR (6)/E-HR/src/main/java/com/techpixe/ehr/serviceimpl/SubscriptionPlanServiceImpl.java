package com.techpixe.ehr.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.ehr.entity.SubscriptionPlan;
import com.techpixe.ehr.repository.SubscriptionPlanRepository;
import com.techpixe.ehr.service.SubscriptionPlanService;



@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService{

	@Autowired
	 SubscriptionPlanRepository subscriptionPlanRepository;

	
	
	@Override
    public SubscriptionPlan createSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanRepository.save(subscriptionPlan);
    }

    @Override
    public SubscriptionPlan updateSubscriptionPlan(Long id, SubscriptionPlan subscriptionPlan) {
        Optional<SubscriptionPlan> existingPlan = subscriptionPlanRepository.findById(id);
        if (existingPlan.isPresent()) {
            SubscriptionPlan planToUpdate = existingPlan.get();
            planToUpdate.setPlanType(subscriptionPlan.getPlanType());
            planToUpdate.setAmount(subscriptionPlan.getAmount());
            planToUpdate.setStartDate(subscriptionPlan.getStartDate());
            planToUpdate.setEndDate(subscriptionPlan.getEndDate());
            return subscriptionPlanRepository.save(planToUpdate);
        }
        return null;
    }

    @Override
    public SubscriptionPlan getSubscriptionPlanById(Long id) {
        return subscriptionPlanRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSubscriptionPlan(Long id) {
        subscriptionPlanRepository.deleteById(id);
    }

    @Override
    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll();
    }
	
}
