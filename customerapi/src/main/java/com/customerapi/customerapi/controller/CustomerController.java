/*
 * Simple rest example
 * by Berihun Hadis
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
//use getmapping to retrieve all customer data from db
    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }
//use postmapping to insert customer data to db
    @PostMapping("/customer")
    public String addCustomer(@Validated @RequestBody Customer customer) {
        customerRepo.save(customer);
        return "customer registered!";
    }
//use putmapping to modify existing customer data from db
    @PutMapping("/customer/id")
    public String updateCustomer(@PathVariable(value = "id") long id, @Validated @RequestBody Customer customer) {
       //optional return data if it exists without any error if it doesn't exist any record with the specified id
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
    //use deletemapping to remove existing customer data from db
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
