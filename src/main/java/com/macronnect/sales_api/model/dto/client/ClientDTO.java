package com.macronnect.sales_api.model.dto.client;

import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.enums.ClientStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientDTO {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final ClientStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ClientDTO(Long id, String name, String email, String phone, String address, ClientStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public ClientStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
