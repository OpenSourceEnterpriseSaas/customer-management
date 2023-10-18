package com.nocodenano.customermanagement.usecases.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocodenano.customermanagement.Customer;
import com.nocodenano.customermanagement.CustomerControllerActivities;
import com.nocodenano.customermanagement.MockCustomerData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerControllerActivities.class)
public class DeleteCustomerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @Test
    void expectNotTo_DeleteCustomer() throws Exception {
        UUID existingCustomerUUID = UUID.randomUUID();
        Customer customer = MockCustomerData.createValid(
                existingCustomerUUID,
                "David",
                "King",
                "davidking@code.net");

        mockMvc.perform(delete("/customers/{customerId}", existingCustomerUUID)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string(containsString("404 NOT_FOUND Not Found")));
    }

    @Test
    void expectTo_DeleteExistingCustomer() throws Exception {
        // create the customer to delete first name
        UUID existingCustomerUUID = createCustomerToUpdate();
        assertThat(existingCustomerUUID).isNotNull();

        mockMvc.perform(delete("/customers/{customerId}", existingCustomerUUID)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string(containsString("status=204 NO_CONTENT")));
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
