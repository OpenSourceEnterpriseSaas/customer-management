package com.nocodenano.customermanagementboon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
public class CreateNewCustomerUseCaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void should_CreateNewCustomer() throws Exception {
        UUID customerIdentifier = UUID.randomUUID();
        Customer customer = MockCustomerData.createValid(
                customerIdentifier,
                "David",
                "King",
                "davidking@code.net");
        String customerJSON = mapper.writeValueAsString(customer);

        mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content(customerJSON))
                .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void shouldNot_CreateNewCustomer_InvalidEmail() throws Exception {
        UUID customerIdentifier = UUID.randomUUID();
        Customer customer = MockCustomerData.createValid(
                customerIdentifier,
                "Netti",
                "Exhale",
                "davidkingatcode.netti");
        String customerJSON = mapper.writeValueAsString(customer);

        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content(customerJSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
