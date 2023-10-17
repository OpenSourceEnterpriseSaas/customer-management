package com.nocodenano.customermanagement.usecases.create;

import com.nocodenano.customermanagement.Customer;
import com.nocodenano.customermanagement.MockCustomerData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCustomerTest {

    @Test
    void expectTo_CreateNewCustomer() {
        UUID customerIdentifier = UUID.randomUUID();
        Customer customer = MockCustomerData.createValid(
                        customerIdentifier,
                        "David",
                        "King",
                        "davidking@code.net");
        assertThat(customer).isNotNull();
        assertThat(customer.uuid()).isEqualTo(customerIdentifier);
        assertThat(customer.firstName()).isEqualTo("David");
        assertThat(customer.lastName()).isEqualTo("King");
        assertThat(customer.email()).isEqualTo("davidking@code.net");
    }

}
