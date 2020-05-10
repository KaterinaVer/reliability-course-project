package com.course.reliability.rest;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.service.CustomerInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.Optional;

@RestController
@Path("/customer-information")
@AllArgsConstructor
public class CustomerInformationController {
    private final CustomerInformationService customerInformationService;

    @GetMapping("/")
    public Iterable<CustomerInformation> getAllCustomers() {
        return customerInformationService.getCustomers();
    }

    @GetMapping("/{id}")
    public Optional<CustomerInformation> getCustomerInformation(@PathVariable("id") Integer id) {
        return customerInformationService.getCustomerInformationById(id);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerInformation> createCustomerInformation(@RequestBody CustomerInformation customerInformation) {
        CustomerInformation savedCustomerInformation = customerInformationService.addCustomerInformation(customerInformation);
        return ResponseEntity.ok(savedCustomerInformation);
    }

    @PutMapping("/{id}")
    public void updateCustomerInformation(@PathVariable("id") Integer id, @Valid @RequestBody  CustomerInformation customerInformation) {
        customerInformation.setId(id);

        customerInformationService.addCustomerInformation(customerInformation);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerInformation(@PathVariable("id") Integer id) {
        customerInformationService.deleteCustomerInformation(id);
    }

}
