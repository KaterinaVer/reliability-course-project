package com.course.reliability.rest;

import com.course.reliability.model.CustomerInformation;
import com.course.reliability.service.CustomerInformationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
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
    public ModelAndView createCustomerInformation(CustomerInformation customerInformation) {
        CustomerInformation customerInformation1 = customerInformationService.addCustomerInformation(customerInformation);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", customerInformation1);
        modelAndView.setViewName("add-edit-customer");

        return modelAndView;
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
    public String deleteCustomerInformation(@PathVariable("id") Integer id) {
        customerInformationService.deleteCustomerInformation(id);

        return "redirect:/customers";
    }

}
