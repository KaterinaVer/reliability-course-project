package com.course.reliability.rest;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.service.CustomerInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/customer-information")
@AllArgsConstructor
public class CustomerInformationController {
    private final CustomerInformationService customerInformationService;

    @RequestMapping
    public String getAllCustomers(final Model model) {
        Iterable<CustomerInformation> customers = customerInformationService.getCustomers();

        model.addAttribute("customers", customers);
        return "list-customers";
    }

    @RequestMapping("/{id}")
    public Optional<CustomerInformation> getCustomerInformation(@PathVariable("id") Integer id) {
        return customerInformationService.getCustomerInformationById(id);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String createCustomerInformation(CustomerInformation customerInformation) {
        customerInformationService.addCustomerInformation(customerInformation);
        return "redirect:/";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String updateCustomerInformation(Model model, @PathVariable("id") Optional<Integer> id) {
        if (id.isPresent()) {
            Optional<CustomerInformation> customerInformation = customerInformationService.getCustomerInformationById(id.get());
            model.addAttribute("customer", customerInformation);
        } else {
            model.addAttribute("customer", new CustomerInformation());
        }

        return "add-edit-customer";
    }

    @RequestMapping("/delete/{id}")
    public void deleteCustomerInformation(@PathVariable("id") Integer id) {
        customerInformationService.deleteCustomerInformation(id);
    }

}
