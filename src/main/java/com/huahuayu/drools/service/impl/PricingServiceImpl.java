package com.huahuayu.drools.service.impl;

import com.huahuayu.drools.model.Order;
import com.huahuayu.drools.model.Result;
import com.huahuayu.drools.service.KieService;
import com.huahuayu.drools.service.PricingService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingServiceImpl implements PricingService {
    @Autowired
    private KieService kieService;

    @Override
    public Result getTheResult (Order order){
        /**
         * get discount by customer type
         */
        KieSession kieSession1 = kieService.getKieSession("DiscountByCustomer");
        Result result = new Result(order);
        kieSession1.insert(order);
        kieSession1.insert(result);
        kieSession1.fireAllRules();
        kieSession1.dispose();

        /**
         * get reduction by payment method
         */
        KieSession kieSession2 = kieService.getKieSession("ReductionByPayment");
        kieSession2.insert(order);
        kieSession2.insert(result);
        kieSession2.fireAllRules();
        kieSession2.dispose();

        return result;
    }
}
