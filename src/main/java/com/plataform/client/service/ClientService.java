package com.plataform.client.service;

import com.plataform.client.domain.Client;
import com.plataform.client.dto.SearchDTO;
import com.plataform.client.repository.ClientRepository;
import com.plataform.client.specification.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> findPageable(SearchDTO searchDTO) {
        Pageable paging = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
        ClientSpecification specification = new ClientSpecification();
        specification.parseQuery(searchDTO.getQuery());
        return this.clientRepository.findAll(specification, paging);
    }

    public Client createNewClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            clientRepository.delete(client);
        });
    }
}
