package com.platform.client.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.client.domain.Client;
import com.platform.client.repository.ClientRepository;
import com.platform.client.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientResourceTest {

    private MockMvc mvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    private final static String RESOURCE_URL = "/api/v1/client";

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new ClientResource(clientService)).build();
        clientRepository.deleteAll();
    }

    public Client createClient(Boolean isSaving) {
        Client client = Client.builder().fullName("Teste").age(20).build();
        return isSaving ? clientRepository.save(client): client;
    }

    @Test
    public void testCreate() throws Exception {
        Client client = this.createClient(Boolean.FALSE);
        mvc.perform( MockMvcRequestBuilders
                .post(RESOURCE_URL)
                .content(asJsonString(client))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testCreate11() throws Exception {
        Client client = this.createClient(Boolean.FALSE);
        client.setFullName(null);
        mvc.perform( MockMvcRequestBuilders
                .post(RESOURCE_URL)
                .content(asJsonString(client))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testUpdate() throws Exception {
        Client client = this.createClient(Boolean.TRUE);
        client.setFullName("Teste editado");
        mvc.perform( MockMvcRequestBuilders
                .put(RESOURCE_URL)
                .content(asJsonString(client))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Teste editado"));
    }
    @Test
    public void testDelete() throws Exception {
        Client client = this.createClient(Boolean.TRUE);
        mvc.perform( MockMvcRequestBuilders.delete(RESOURCE_URL + "/{id}", client.getId()) )
                .andExpect(status().isAccepted());
    }

    @Test
    public void testFindAll() throws Exception {
        this.createClient(Boolean.TRUE);
        mvc.perform( MockMvcRequestBuilders
                .get(RESOURCE_URL + "?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", is(1)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
