package com.nocodenano.customermanagement.usecases.updates;

import com.nocodenano.customermanagement.Customer;
import com.nocodenano.customermanagement.MockCustomerData;
import com.nocodenano.customermanagement.UpdateEmailCustomer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateEmailTest {

    @Test
    void expectTo_UpdateEmail_ofCustomer() {
        UUID customerIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");

        UpdateEmailCustomer updateEmailCustomer =
                MockCustomerData.createUpdateEmailCustomer(
                        customerIdentifier,
                        "davidking@codeitwithlove.com"
                );

        // business invariant: customer should already exist
        assertThat(updateEmailCustomer.uuid())
                .isEqualByComparingTo(existingCustomer.uuid());

        // business invariant: the field to be updated should not be the same value
        assertThat(updateEmailCustomer.email())
                .isNotEqualToIgnoringCase(existingCustomer.email());

        Customer updatedCustomerFirstName = new Customer(
                existingCustomer.uuid(),
                existingCustomer.firstName(),
                existingCustomer.lastName(),
                updateEmailCustomer.email()
        );

        // customer updated, verify
        assertThat(updatedCustomerFirstName)
                .hasFieldOrPropertyWithValue("uuid", existingCustomer.uuid())
                .hasFieldOrPropertyWithValue("firstName", existingCustomer.firstName())
                .hasFieldOrPropertyWithValue("lastName", existingCustomer.lastName())
                .hasFieldOrPropertyWithValue("email", updateEmailCustomer.email());
    }
    @Test
    void expectNotTo_UpdateEmail_ofCustomer() {
        UUID customerIdentifier = UUID.randomUUID();
        UUID rougueIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@codeitwithlove.net");

        UpdateEmailCustomer updateEmailCustomer =
                MockCustomerData.createUpdateEmailCustomer(
                        customerIdentifier,
                        "davidking@codeitwithlove.net"
                );

        // business invariant, don't update if names are the same, EQUALITY TEST
        assertThat(existingCustomer.email()).isEqualTo(updateEmailCustomer.email());

        // business invariant, customer not found, don't update, NON-EXISTING CUSTOMER
        assertThat(existingCustomer.uuid()).isNotEqualByComparingTo(rougueIdentifier);
    }

}
