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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerControllerActivities.class)
public class DeleteCustomerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @Test
    void expectTo_DeleteCustomer() throws Exception {
        UUID existingCustomerUUID = UUID.randomUUID();
        Customer customer = MockCustomerData.createValid(
                existingCustomerUUID,
                "David",
                "King",
                "davidking@code.net");

        String customerJSON = mapper.writeValueAsString(customer);

        mockMvc.perform(delete("/customers/{customerId}", existingCustomerUUID)
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
