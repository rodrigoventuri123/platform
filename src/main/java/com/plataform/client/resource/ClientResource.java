package com.plataform.client.resource;

import com.plataform.client.domain.Client;
import com.plataform.client.dto.SearchDTO;
import com.plataform.client.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ClientResource {

    private ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "client", method = RequestMethod.GET)
    public Page<Client> findClientPageable(SearchDTO searchDTO) {
        return clientService.findPageable(searchDTO);
    }

    @RequestMapping(value = "client", method = RequestMethod.POST)
    public ResponseEntity<Client> createNewContact(@Valid @RequestBody Client client) {
        Client newClient = clientService.createNewClient(client);
        return ResponseEntity.ok(newClient);
    }

    @RequestMapping(value = "client", method = RequestMethod.PUT)
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) {
        Client newClient = clientService.updateClient(client);
        return ResponseEntity.ok(newClient);
    }

    @RequestMapping(value = "client/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") Long id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
