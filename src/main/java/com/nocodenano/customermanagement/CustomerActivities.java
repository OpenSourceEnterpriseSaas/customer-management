package com.nocodenano.customermanagement;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public interface CustomerActivities {
    UUID create(Customer customer);
    String updateFirstname(UUID customerId, UpdateFirstNameCustomer updateFirstNameCustomer);
    String updateLastname(UUID customerId, UpdateLastNameCustomer updateLastNameCustomer);
    String changeEmailAddress(UUID customerId, UpdateEmailCustomer updateEmailCustomer);
    void delete(UUID customerId);
    Optional<Customer> findById(Customer customer);
    Iterator<Customer> findAll(Customer customer);

}
