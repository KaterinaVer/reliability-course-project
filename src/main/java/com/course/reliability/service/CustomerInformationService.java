package com.course.reliability.service;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.repository.CustomerInformationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerInformationService {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerInformationService.class);
    private CustomerInformationRepository customerInformationRepository;
    private ReliabilityService reliabilityService;

    public Iterable<CustomerInformation> getCustomers(){
        return customerInformationRepository.findAll();
    }

    public Optional<CustomerInformation> getCustomerInformationById(final Integer id){
        if(id == null || id <= 0){
            throw new RuntimeException("Customer's ID should be more than 0 or not a null");
        }
        return customerInformationRepository.findById(id);
    }

    public CustomerInformation addCustomerInformation(final CustomerInformation customerInformation)  {
        customerInformation.setReliability(reliabilityService.predictReliability(customerInformation));

        return customerInformationRepository.save(customerInformation);
    }

    public void deleteCustomerInformation(Integer id){
        if(id == null || id <= 0){
            throw new RuntimeException("Customer's ID should be more than 0 or not a null");
        }
        customerInformationRepository.deleteById(id);
    }

}
