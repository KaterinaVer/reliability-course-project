package com.course.reliability.service;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.repository.CustomerInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerInformationService {

    private CustomerInformationRepository customerInformationRepository;
    private ReliabilityService reliabilityService;

    public Page<CustomerInformation> getCustomers(final Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<CustomerInformation> list;

        List<CustomerInformation> customerInformations = customerInformationRepository.findAllByOrderByIdDesc();

        if (customerInformations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, customerInformations.size());
            list = customerInformations.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), customerInformations.size());
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
