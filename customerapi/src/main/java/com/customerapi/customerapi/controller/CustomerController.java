/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.customerapi.customerapi.controller;


import com.customerapi.customerapi.domain.Customer;
import com.customerapi.customerapi.repo.CustomerRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    @PostMapping("/customer")
    public String addCustomer(@Validated @RequestBody Customer customer) {
        customerRepo.save(customer);
        return "customer registered!";
    }

    @PutMapping("/customer/id")
    public String updateCustomer(@PathVariable(value = "id") long id, @Validated @RequestBody Customer customer) {
        Optional<Customer> c = customerRepo.findById(id);
        String message="";
        if (c.isPresent()) {
            c.get().setFirstName(customer.getFirstName());
            c.get().setLastName(customer.getLastName());
            c.get().setPhone(customer.getPhone());
            customerRepo.save(c.get());
            message="customer successfully updated";
        }else{
            message="customer not updated";
        }
        return message;
    }
    @DeleteMapping("customer/id")
    public String deleteCustomer(@PathVariable(value = "id" )long id){
        Optional<Customer> c = customerRepo.findById(id);
        String message="";
        if (c.isPresent()) {
            customerRepo.delete(c.get());
            message="customer successfully deleted";
        }else{
            message="customer not deleted";
        }
        return message;
    }
}
