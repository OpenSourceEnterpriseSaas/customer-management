package com.nocodenano.customermanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CustomerControllerActivities
    implements CustomerActivities {

    private final ObjectMapper mapper;
    Map<UUID, Customer> database;

    public CustomerControllerActivities(final ObjectMapper mapper) {
        this.database = new ConcurrentHashMap<>();
        this.mapper = mapper;
    }

    @Override
    @PostMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID create(@Valid @RequestBody final Customer customer) {
        this.database.put(customer.uuid(), customer);
        return customer.uuid();
    }

    @Override
    @PutMapping(value = "/customers/{customerId}/firstname", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateFirstname(@PathVariable("customerId") UUID customerId,
                                  @Valid @RequestBody final UpdateFirstNameCustomer updateFirstNameCustomer) {
        Customer existingCustomer = this.database.get(customerId);
        if (null == existingCustomer) {
            log.debug("updateFirstname, customer not found for id " + customerId);
            log.debug("cache: " + this.database);
            return ResponseEntity.notFound().build().toString();
        }

        // business invariants:
        // 1) customer firstname should not already exist for this customer
        if (existingCustomer.firstName().equalsIgnoreCase(updateFirstNameCustomer.firstName())) {
            log.debug("updateFirstname, customer with first name already exists not updating.");
            return createWebResponse(HttpStatusCode.valueOf(400), null, "Customer first name updated.");
        }


        // update the first name
        Customer toUpdate = new Customer(
                existingCustomer.uuid(),
                updateFirstNameCustomer.firstName(),
                existingCustomer.lastName(),
                existingCustomer.email()
        );

        // result
        return createWebResponse(HttpStatusCode.valueOf(200), toUpdate, "Customer first name updated.");
    }


    @Override
    @PutMapping(value = "/customers/{customerId}/lastname", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateLastname(@PathVariable("customerId") UUID customerId,
                                 @Valid @RequestBody final UpdateLastNameCustomer updateLastNameCustomer) {
        Customer existingCustomer = this.database.get(customerId);
        if (null == existingCustomer) {
            log.debug("updateLastname, customer not found for id " + customerId);
            log.debug("cache: " + this.database);
            return ResponseEntity.notFound().build().toString();
        }

        // business invariants:
        // 1) customer firstname should not already exist for this customer
        if (existingCustomer.lastName().equalsIgnoreCase(updateLastNameCustomer.lastName())) {
            log.debug("updateLastname, customer with last name already exists not updating.");
            return createWebResponse(HttpStatusCode.valueOf(400), null, "Customer last name updated.");
        }


        // update the first name
        Customer toUpdate = new Customer(
                existingCustomer.uuid(),
                existingCustomer.firstName(),
                updateLastNameCustomer.lastName(),
                existingCustomer.email()
        );

        // result
        return createWebResponse(HttpStatusCode.valueOf(200), toUpdate, "Customer last name updated.");
    }

    @Override
    @PutMapping(value = "/customers/{customerId}/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeEmailAddress(@PathVariable("customerId") UUID customerId,
                                     @Valid @RequestBody final UpdateEmailCustomer updateEmailCustomer) {
        Customer existingCustomer = this.database.get(customerId);
        if (null == existingCustomer) {
            log.debug("changeEmailAddress, customer not found for id " + customerId);
            log.debug("cache: " + this.database);
            return ResponseEntity.notFound().build().toString();
        }

        // business invariants:
        // 1) customer firstname should not already exist for this customer
        if (existingCustomer.email().equalsIgnoreCase(updateEmailCustomer.email())) {
            log.debug("changeEmailAddress, customer with EMAIL already exists not updating.");
            return createWebResponse(HttpStatusCode.valueOf(400), null, "Customer EMAIL updated.");
        }


        // update the first name
        Customer toUpdate = new Customer(
                existingCustomer.uuid(),
                existingCustomer.firstName(),
                existingCustomer.lastName(),
                updateEmailCustomer.email()
        );

        // result
        return createWebResponse(HttpStatusCode.valueOf(200), toUpdate, "Customer EMAIL updated.");
    }

    @Override
    @DeleteMapping(value = "/customers/{customerId}")
    public String delete(@PathVariable("customerId") UUID customerId) {
        Customer existingCustomer = this.database.get(customerId);
        if (null == existingCustomer) {
            log.debug("delete, customer not found for id " + customerId);
            log.debug("cache: " + this.database);
            return ResponseEntity.notFound().build().toString();
        }

        this.database.remove(existingCustomer);
        return createWebResponse(HttpStatusCode.valueOf(204), null, "Customer DELETED.");
    }

    @Override
    @GetMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findById(@PathVariable("customerId") UUID customerId) {
        return this.database.get(customerId) != null ?
                this.database.get(customerId).toString() :
                ResponseEntity.notFound().build().toString();
    }

    @Override
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Customer> findAll() {
        if (this.database.values().isEmpty()) throw new RuntimeException("No customers found.");
        return this.database.values().stream().collect(Collectors.toUnmodifiableSet());
    }


    private static String createWebResponse(final HttpStatusCode httpStatusCode,
                                            final Customer customer, final String message) {
        Map<String, String> result = new ConcurrentHashMap<>();
        result.put("status", httpStatusCode.toString());
        result.put("payload", customer == null ? "" : customer.toString());
        result.put("message", message);
        return result.toString();
    }
}
