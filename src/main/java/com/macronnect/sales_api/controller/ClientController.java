package com.macronnect.sales_api.controller;

import com.macronnect.sales_api.model.dto.client.ClientDTO;
import com.macronnect.sales_api.model.dto.client.CreateClientRequest;
import com.macronnect.sales_api.model.dto.client.UpdateClientRequest;
import com.macronnect.sales_api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Operations related to client management")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            summary = "Create a new client",
            description = "Creates a new client if the email address is not already registered."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(
            @Valid @RequestBody CreateClientRequest request) {

        ClientDTO response = clientService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Update client",
            description = "Updates an existing client's information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientRequest request){

        return ResponseEntity.ok(clientService.update(id, request));
    }

    @Operation(
            summary = "Get all clients",
            description = "Returns a paginated list of active clients."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<ClientDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.getAll(pageable));
    }

    @Operation(
            summary = "Get client by ID",
            description = "Returns a client by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getByID(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getById(id));
    }

    @Operation(
            summary = "Reactivate client",
            description = "Changes the client's status from INACTIVE to ACTIVE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client reactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ClientDTO> activate(
            @PathVariable Long id) {

        ClientDTO client = clientService.activate(id);

        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Deactivate client",
            description = "Performs a logical deletion by changing the client's status to INACTIVE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
