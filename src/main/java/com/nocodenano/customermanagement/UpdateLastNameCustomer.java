package com.nocodenano.customermanagement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateLastNameCustomer(@NotNull UUID uuid,
         @NotNull @Size(min=2, max=255) String lastName) {}
