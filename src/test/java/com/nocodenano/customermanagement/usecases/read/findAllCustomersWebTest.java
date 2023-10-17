package com.nocodenano.customermanagement.usecases.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocodenano.customermanagement.CustomerControllerActivities;
import com.nocodenano.customermanagement.MockCustomerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerControllerActivities.class)
public class findAllCustomersWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    Set<UUID> mockCustomerUUIDs = new HashSet<>();

    @BeforeEach
    void setUp() throws Exception {
        IntStream.rangeClosed(1,3).forEach(customerCounter -> {
            try {
                mockCustomerUUIDs.add(createCustomerToUpdate());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void shouldFindAllCustomersOrEmptyDataset() throws Exception {
        assertThat(mockCustomerUUIDs).isNotEmpty();

        mockMvc.perform(get("/customers")
                        .contentType("application/json"))
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
