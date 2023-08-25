package ec.edu.espe.arquitectura.banquito.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.arquitectura.banquito.controller.ClientController;
import ec.edu.espe.arquitectura.banquito.dto.ClientAddressRS;
import ec.edu.espe.arquitectura.banquito.dto.ClientPhoneRS;
import ec.edu.espe.arquitectura.banquito.dto.ClientRQ;
import ec.edu.espe.arquitectura.banquito.dto.ClientRS;
import ec.edu.espe.arquitectura.banquito.model.Client;
import ec.edu.espe.arquitectura.banquito.model.ClientAddress;
import ec.edu.espe.arquitectura.banquito.model.ClientPhone;
import ec.edu.espe.arquitectura.banquito.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    private ClientRS clientRS;

    private Client client;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        this.clientController = new ClientController(this.clientService);
        ClientPhone phone = ClientPhone.builder().phoneType("OFF").phoneNumber("1234567890")
                .isDefault(true).state("ACT").build();
        ClientAddress address = ClientAddress.builder().locationId("1")
                .isDefault(true)
                .latitude(Float.parseFloat(String.valueOf(17.908736)))
                .line1("Alcides Enriquez").line2("Chasqui").longitude(Float.parseFloat(String.valueOf(89.908736)))
                .state("ACT").build();
        List<ClientPhone> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phone);
        List<ClientAddress> addresses = new ArrayList<>();
        addresses.add(address);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse("1990-01-15");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.client = Client.builder().id("123456").branchId("branch123").uniqueKey("key123")
                .typeDocumentId("IDE").documentId("1722620489")
                .firstName("David").lastName("Tamayo")
                .gender("MAS").birthDate(birthDate)
                .emailAddress("datamayo4@espe.edu.ec")
                .creationDate(new Date()).activationDate(new Date())
                .lastModifiedDate(new Date()).role(null)
                .state("ACT").closedDate(null).comments("test")
                .password("123").addresses(addresses)
                .phoneNumbers(phoneNumbers).build();

    }

    @Test
    void testObtainByDocumentTypeAndDocumentIdOk()  {
        ClientPhoneRS phoneRS = ClientPhoneRS.builder().phoneType("OFF").phoneNumber("1234567890").isDefault(true).state("ACT").build();
        ClientAddressRS addressRS = ClientAddressRS.builder().locationId("1").isDefault(true)
                .latitude(Float.parseFloat(String.valueOf(17.908736)))
                .line1("Alcides Enriquez").line2("Chasqui")
                .longitude(Float.parseFloat(String.valueOf(89.908736))).state("ACT").build();
        List<ClientPhoneRS> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneRS);
        List<ClientAddressRS> addresses = new ArrayList<>();
        addresses.add(addressRS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse("1990-01-15");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.clientRS = ClientRS.builder().branchId("branch123")
                .uniqueKey("key123").typeDocumentId("IDE")
                .documentId("1722620489").firstName("David")
                .lastName("Tamayo").gender("MAS").birthDate(birthDate)
                .emailAddress("datamayo4@espe.edu.ec").creationDate(new Date())
                .activationDate(new Date())
                .lastModifiedDate(new Date()).role(null).
                state("ACT").closedDate(null).comments("test").addresses(addresses).phoneNumbers(phoneNumbers).build();
        String type = "IDE";
        String id = "1722620489";
        when(this.clientService.obtainClientByDocumentTypeAndDocumentId(type, id)).thenReturn(this.clientRS);
        ResponseEntity<ClientRS> response = this.clientController.obtainByDocumentTypeAndDocumentId(type, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.clientRS, response.getBody());
    }

    @Test
    void testObtainByDocumentTypeAndDocumentIdNotFound(){
        String type = "RUC";
        String id = "1722620489";
        when(this.clientService.obtainClientByDocumentTypeAndDocumentId(type, id)).thenThrow(new RuntimeException());
        ResponseEntity<ClientRS> response = this.clientController.obtainByDocumentTypeAndDocumentId(type, id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void testClientUpdateOk() throws Exception {
        /*String type = "IDE";
        String id = "1722620489";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse("1999-07-20");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ClientRQ clientRQ = ClientRQ.builder().branchId("branch222")
                .typeDocumentId("IDE").documentId("1722620489")
                .firstName("Lucas").lastName("Hernandez")
                .gender("MAS").birthDate(birthDate)
                .emailAddress("gugli10@hotmail.com").role(null)
                .comments("modificado").password("1234")
                .state("ACT").build();
        when(this.clientService.updateClient(eq(clientRQ), eq(type), eq(id))).thenReturn(this.client);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v2/clients/updateClient/{documentType}/{documentId}", type, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRQ)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(this.client)));*/
    }

    @Test
    void testClientUpdateBadRequest() throws Exception {
        /*String type = "IDE";
        String id = "1722620489";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse("1999-07-20");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ClientRQ clientRQ = ClientRQ.builder().branchId("branch222")
                .typeDocumentId("IDE").documentId("1722620489")
                .firstName("Lucas").lastName("Hernandez")
                .gender("MAS").birthDate(birthDate)
                .emailAddress("gugli10@hotmail.com").role(null)
                .comments("modificado").password("1234")
                .state("ACT").build();
        when(this.clientService.updateClient(eq(clientRQ), eq(type), eq(id))).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v2/clients/updateClient/{documentType}/{documentId}", type, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRQ)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());*/
    }
}

