package com.nocodenano.customermanagement.usecases.updates;

import com.nocodenano.customermanagement.Customer;
import com.nocodenano.customermanagement.MockCustomerData;
import com.nocodenano.customermanagement.UpdateFirstNameCustomer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateFirstNameTest {

    @Test
    void expectTo_UpdateFirstName_ofCustomer() {
        UUID customerIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");

        UpdateFirstNameCustomer updateFirstNameCustomer =
                MockCustomerData.createUpdateFirstnameCustomer(
                        customerIdentifier,
                        "Yavid"
                );

        // business invariant: customer should already exist
        assertThat(updateFirstNameCustomer.uuid())
                .isEqualByComparingTo(existingCustomer.uuid());

        // business invariant: the field to be updated should not be the same value
        assertThat(updateFirstNameCustomer.firstName())
                .isNotEqualToIgnoringCase(existingCustomer.firstName());

        Customer updatedCustomerFirstName = new Customer(
                existingCustomer.uuid(),
                updateFirstNameCustomer.firstName(),
                existingCustomer.lastName(),
                existingCustomer.email()
        );

        // customer updated, verify
        assertThat(updatedCustomerFirstName)
                .hasFieldOrPropertyWithValue("uuid", existingCustomer.uuid())
                .hasFieldOrPropertyWithValue("firstName", updateFirstNameCustomer.firstName())
                .hasFieldOrPropertyWithValue("lastName", existingCustomer.lastName())
                .hasFieldOrPropertyWithValue("email", existingCustomer.email());
    }

    @Test
    void expectToNot_UpdateFirstname_ofCustomer_PreexistingFirstname() {
        UUID customerIdentifier = UUID.randomUUID();
        UUID rougueIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");

        UpdateFirstNameCustomer updateFirstNameCustomer =
                MockCustomerData.createUpdateFirstnameCustomer(
                        rougueIdentifier,
                        "David"
                );

        // business invariant, don't update if names are the same
        assertThat(existingCustomer.firstName()).isEqualTo(updateFirstNameCustomer.firstName());

        // business invariant, customer not found, don't update
        assertThat(existingCustomer.uuid()).isNotEqualByComparingTo(rougueIdentifier);

    }
}
