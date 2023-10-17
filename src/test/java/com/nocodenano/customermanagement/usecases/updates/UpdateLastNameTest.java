package com.nocodenano.customermanagement.usecases.updates;

import com.nocodenano.customermanagement.Customer;
import com.nocodenano.customermanagement.MockCustomerData;
import com.nocodenano.customermanagement.UpdateLastNameCustomer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateLastNameTest {
    @Test
    void expectTo_UpdateLastName_ofCustomer() {
        UUID customerIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");

        UpdateLastNameCustomer updateLastNameCustomer =
                MockCustomerData.createUpdateLastnameCustomer(
                        customerIdentifier,
                        "NonKlingon"
                );

        // business invariant: customer should already exist
        assertThat(updateLastNameCustomer.uuid())
                .isEqualByComparingTo(existingCustomer.uuid());

        // business invariant: the field to be updated should not be the same value
        assertThat(updateLastNameCustomer.lastName())
                .isNotEqualToIgnoringCase(existingCustomer.lastName());

        Customer updatedCustomerFirstName = new Customer(
                existingCustomer.uuid(),
                existingCustomer.firstName(),
                updateLastNameCustomer.lastName(),
                existingCustomer.email()
        );

        // customer updated, verify
        assertThat(updatedCustomerFirstName)
                .hasFieldOrPropertyWithValue("uuid", existingCustomer.uuid())
                .hasFieldOrPropertyWithValue("firstName", existingCustomer.firstName())
                .hasFieldOrPropertyWithValue("lastName", updateLastNameCustomer.lastName())
                .hasFieldOrPropertyWithValue("email", existingCustomer.email());
    }

    @Test
    void expectNotTo_UpdateLastName_ofCustomer_PreexistingLastname() {
        UUID customerIdentifier = UUID.randomUUID();
        UUID rougueIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "NonKlingon",
                "davidking@code.net");

        UpdateLastNameCustomer updateLastNameCustomer =
                MockCustomerData.createUpdateLastnameCustomer(
                        customerIdentifier,
                        "NonKlingon"
                );

        // business invariant, don't update if names are the same, EQUALITY TEST
        assertThat(existingCustomer.lastName()).isEqualTo(updateLastNameCustomer.lastName());

        // business invariant, customer not found, don't update, NON-EXISTING CUSTOMER
        assertThat(existingCustomer.uuid()).isNotEqualByComparingTo(rougueIdentifier);

    }
}
