package com.course.reliability.service;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.repository.CustomerInformationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CustomerInformationService {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerInformationService.class);
    private CustomerInformationRepository customerInformationRepository;
    private ReliabilityService reliabilityService;

    public Page<CustomerInformation> getCustomers(final Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<CustomerInformation> list;

        Iterable<CustomerInformation> customerInformations = customerInformationRepository.findAll();
        List<CustomerInformation> customerInformationList =
                StreamSupport.stream(customerInformations.spliterator(), false)
                        .collect(Collectors.toList());

        if (customerInformationList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, customerInformationList.size());
            list = customerInformationList.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), customerInformationList.size());
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
