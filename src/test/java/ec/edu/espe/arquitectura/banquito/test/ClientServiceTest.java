package ec.edu.espe.arquitectura.banquito.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ec.edu.espe.arquitectura.banquito.model.Client;
import ec.edu.espe.arquitectura.banquito.model.ClientAddress;
import ec.edu.espe.arquitectura.banquito.model.ClientPhone;
import ec.edu.espe.arquitectura.banquito.repository.ClientRepository;
import ec.edu.espe.arquitectura.banquito.service.ClientService;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private ClientPhone phone;
    private ClientAddress address;
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

    }

    @Test
    void testObtainClientByDocumentTypeAndDocumentId() {

        try {
            List<ClientPhone> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(this.phone);
            List<ClientAddress> addresses = new ArrayList<>();
            addresses.add(this.address);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate;
            birthDate = dateFormat.parse("1990-01-15");
            Date creationDate = new Date();
            Client client = Client.builder().id("123456").branchId("branch123").uniqueKey("key123")
                    .typeDocumentId("IDE").documentId("1722620489").firstName("David")
                    .lastName("Tamayo").gender("MAS").birthDate(birthDate)
                    .emailAddress("datamayo4@espe.edu.ec").creationDate(creationDate)
                    .activationDate(new Date()).lastModifiedDate(new Date()).role(null)
                    .state("ACT").closedDate(null).comments("test").password("123")
                    .addresses(addresses).phoneNumbers(phoneNumbers).build();

            when(this.clientRepository.findFirstByTypeDocumentIdAndDocumentId("IDE", "1722620489")).thenReturn(client);
            assertDoesNotThrow(() -> {
                this.clientService.obtainClientByDocumentTypeAndDocumentId("IDE", "1722620489");
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
