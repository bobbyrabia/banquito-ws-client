package ec.edu.espe.arquitectura.banquito.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ec.edu.espe.arquitectura.banquito.dto.ClientRQ;
import ec.edu.espe.arquitectura.banquito.model.Client;
import ec.edu.espe.arquitectura.banquito.model.ClientAddress;
import ec.edu.espe.arquitectura.banquito.model.ClientPhone;
import ec.edu.espe.arquitectura.banquito.repository.ClientRepository;
import ec.edu.espe.arquitectura.banquito.service.ClientService;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private ClientPhone phone;
    private ClientAddress address;
    private List<ClientPhone> phoneNumbers;
    private List<ClientAddress> addresses;

    

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        this.clientService = new ClientService(this.clientRepository);
        this.phone = ClientPhone.builder().phoneType("OFF").phoneNumber("1234567890").isDefault(true).state("ACT")
                .build();
        this.address = ClientAddress.builder().locationId("1").isDefault(true).latitude(null).line1("Alcides Enriquez")
                .line2("Chasqui").longitude(null).state("ACT").build();
        this.phoneNumbers = new ArrayList<>();
        phoneNumbers.add(this.phone);
        this.addresses = new ArrayList<>();
        addresses.add(this.address);
    }

    @Test
    void testObtainClientByDocumentTypeAndDocumentId() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate;
            birthDate = dateFormat.parse("1990-01-15");
            Client client = Client.builder().id("123456").branchId("branch123").uniqueKey("key123")
                    .typeDocumentId("IDE").documentId("1722620489").firstName("David")
                    .lastName("Tamayo").gender("MAS").birthDate(birthDate)
                    .emailAddress("datamayo4@espe.edu.ec").creationDate(new Date())
                    .activationDate(new Date()).lastModifiedDate(new Date()).role(null)
                    .state("ACT").closedDate(null).comments("test").password("123")
                    .addresses(addresses).phoneNumbers(phoneNumbers).build();

            when(this.clientRepository.findFirstByTypeDocumentIdAndDocumentId("IDE", "1722620489")).thenReturn(client);
            assertDoesNotThrow(() -> {
                this.clientService.obtainClientByDocumentTypeAndDocumentId("IDE", "1722620489");
            });
            assertThrows(RuntimeException.class, () -> {
                this.clientService.obtainClientByDocumentTypeAndDocumentId("IDE", "1176356789");
            });
            client.setState("INA");
            assertThrows(RuntimeException.class, () -> {
                this.clientService.obtainClientByDocumentTypeAndDocumentId("IDE", "1722620489");
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testClienCreate(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate;
            birthDate = dateFormat.parse("1999-07-20");
            Date badBirthDate;
            badBirthDate = dateFormat.parse("2023-12-25");
            ClientRQ clientRQ = ClientRQ.builder().branchId("branch123")
                    .typeDocumentId("IDE").documentId("1722620489").firstName("David")
                    .lastName("Tamayo").gender("MAS").birthDate(birthDate)
                    .emailAddress("datamayo4@espe.edu.ec")
                    .role(null).comments("test create").password("123").build();

            when(this.clientRepository.findFirstByTypeDocumentIdAndDocumentId(clientRQ.getTypeDocumentId(), clientRQ.getDocumentId())).thenReturn(null);
            assertDoesNotThrow(() -> {
                this.clientService.clientCreate(clientRQ);
            });
            clientRQ.setBirthDate(badBirthDate);
            assertThrows(RuntimeException.class, () -> {
                this.clientService.clientCreate(clientRQ);
            });
            

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
