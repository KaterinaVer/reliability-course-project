package com.course.reliability.service;

import com.course.reliability.dao.CustomerInformationDao;
import com.course.reliability.model.CustomerInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerInformationService {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerInformationService.class);
    private CustomerInformationDao customerInformationDao;
    private ReliabilityService reliabilityService;

    @Autowired
    public CustomerInformationService(CustomerInformationDao customerInformationDao, ReliabilityService reliabilityService){
        this.customerInformationDao =customerInformationDao;
        this.reliabilityService = reliabilityService;
    }

    public List<CustomerInformation> getCustomers(){
        return customerInformationDao.findAll();
    }

    public CustomerInformation getCustomerInformationById(final Integer id){
        if(id == null || id <= 0){
            throw new RuntimeException("Customer's ID should be more than 0 or not a null");
        }
        return customerInformationDao.getCustomerInformationById(id);
    }

    public Integer addCustomerInformation(final CustomerInformation customerInformation)  {
         final Integer id = customerInformationDao.insertCustomerInformation(customerInformation);
         customerInformationDao.updateCustomerReliability(reliabilityService.predictReliability(customerInformation), id);
         return id;
    }

    public void updateCustomerInformation(final CustomerInformation customerInformation)  {
         customerInformationDao.updateCustomerInformation(customerInformation,reliabilityService.predictReliability(customerInformation));
    }

    public Integer deleteCustomerInformation(Integer id){
        if(id == null || id <= 0){
            throw new RuntimeException("Customer's ID should be more than 0 or not a null");
        }
        return customerInformationDao.deleteCustomerInformation(id);
    }

}
