package com.nocodenano.customermanagement;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CustomerController
    implements CreateNewCustomer {

    @Override
    @PostMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID create(@Valid @RequestBody final Customer customer) {
        return new Customer(customer.uuid(),
                customer.firstName(),
                customer.lastName(),
                customer.email()).uuid();
    }
}
