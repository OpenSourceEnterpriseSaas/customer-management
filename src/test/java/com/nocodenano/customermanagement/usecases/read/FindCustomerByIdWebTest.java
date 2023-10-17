package com.nocodenano.customermanagement.usecases.read;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerControllerActivities.class)
public class FindCustomerByIdWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @Test
    void should_findCustomerById() throws Exception {
        UUID customerIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "NonKlingon",
                "davidking@code.net");

        String customerJSON = mapper.writeValueAsString(existingCustomer);

        mockMvc.perform(get("/customers/{customerId}", existingCustomer.uuid())
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNot_findCustomerById() throws Exception {
        UUID customerIdentifier = UUID.randomUUID();
        UUID rougueIdentifier = UUID.randomUUID();
        Customer existingCustomer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "NonKlingon",
                "davidking@code.net");

        String customerJSON = mapper.writeValueAsString(existingCustomer);

        mockMvc.perform(get("/customers/{customerId}", rougueIdentifier)
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(content().string(containsString("404 NOT_FOUND Not Found")));

    }
}
