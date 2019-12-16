package com.course.reliability.rest;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.service.CustomerInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@Path("/customer-information")
public class CustomerInformationController {
    private final CustomerInformationService customerInformationService;

    @Autowired
    public CustomerInformationController(CustomerInformationService customerInformationService){
        this.customerInformationService = customerInformationService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerInformation> getAllCustomers() {
        return customerInformationService.getCustomers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInformation getCustomerInformation(@PathVariable("id") Integer id) {
        return customerInformationService.getCustomerInformationById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCustomerInformation(@Valid @RequestBody CustomerInformation customerInformation, UriComponentsBuilder ucBuilder) {
        Integer id = customerInformationService.addCustomerInformation(customerInformation);

        UriComponents uriComponent = ucBuilder.path("/customer-information/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponent.toUri()).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCustomerInformation(@PathVariable("id") Integer id, @Valid @RequestBody  CustomerInformation customerInformation) {
        customerInformation.setId(id);

        customerInformationService.updateCustomerInformation(customerInformation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerInformation(@PathVariable("id") Integer id) {
        customerInformationService.deleteCustomerInformation(id);
    }

}
