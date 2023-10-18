package com.nocodenano.customermanagement.usecases.updates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocodenano.customermanagement.CustomerControllerActivities;
import com.nocodenano.customermanagement.MockCustomerData;
import com.nocodenano.customermanagement.UpdateEmailCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerControllerActivities.class)
public class UpdateEmailWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldNot_UpdateCustomerEmail_ExistingCustomerWithEmail() throws Exception {
        // create the customer to update first name
        UUID existingCustomerUUID = createCustomerToUpdate();
        assertThat(existingCustomerUUID).isNotNull();

        // create the update Email customer
        UpdateEmailCustomer updateEmailCustomer =
                MockCustomerData.createUpdateEmailCustomer(
                        existingCustomerUUID,
                        "davidking@code.net"
                );

        String customerJSON = mapper.writeValueAsString(updateEmailCustomer);

        mockMvc.perform(put("/customers/{customerId}/email", existingCustomerUUID)
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(content().string(containsString("400 BAD_REQUEST")));
    }

    @Test
    void should_UpdateCustomerEmail_ExistingCustomerWithEmail() throws Exception {
        // create the customer to update first name
        UUID existingCustomerUUID = createCustomerToUpdate();
        assertThat(existingCustomerUUID).isNotNull();

        // create the update Email customer
        UpdateEmailCustomer updateEmailCustomer =
                MockCustomerData.createUpdateEmailCustomer(
                        existingCustomerUUID,
                        "davidking@codewithlove.com"
                );

        String customerJSON = mapper.writeValueAsString(updateEmailCustomer);

        mockMvc.perform(put("/customers/{customerId}/email", existingCustomerUUID)
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private UUID createCustomerToUpdate() throws Exception {
        UUID customerIdentifier = UUID.randomUUID();
        String existingCustomer = MockCustomerData.createCustomerAsJson(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");
        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content(existingCustomer))
                .andDo(print())
                .andExpect(status().isOk());
        return customerIdentifier;
    }
}
