package com.course.reliability.rest;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.service.CustomerInformationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.awt.print.Book;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
//@RequestMapping("/customer-information")
@AllArgsConstructor
public class CustomerInformationController {
    private final CustomerInformationService customerInformationService;

    @RequestMapping("/customers")
    public String getAllCustomers(final Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<CustomerInformation> customerInformationPage =
                customerInformationService.getCustomers(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("customerPage", customerInformationPage);

        int totalPages = customerInformationPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

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
