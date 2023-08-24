package ec.edu.espe.arquitectura.banquito.test;

import ec.edu.espe.arquitectura.banquito.model.GroupCompany;
import ec.edu.espe.arquitectura.banquito.model.GroupCompanyMember;
import ec.edu.espe.arquitectura.banquito.repository.ClientRepository;
import ec.edu.espe.arquitectura.banquito.repository.GroupCompanyRepository;
import ec.edu.espe.arquitectura.banquito.service.GroupCompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupCompanyTest {
    private GroupCompany company;
    @InjectMocks
    private GroupCompanyService groupCompanyService;

    @Mock
    private GroupCompanyRepository groupCompanyRepository;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        List<GroupCompanyMember> members = new ArrayList<>();
        GroupCompanyMember member = GroupCompanyMember.builder().groupRole("Admin")
                .clientId("12345")
                .state("Active")
                .creationDate(new Date())
                .lastModifiedDate(new Date())
                .build();
        members.add(member);
        this.groupCompanyService = new GroupCompanyService(this.groupCompanyRepository, this.clientRepository);
        this.company = GroupCompany.builder().branchId("branch123").locationId("location1").
                uniqueKey("uniqueKey123")
                .groupName("Example Group")
                .emailAddress("example@example.com")
                .phoneNumber("123-456-7890")
                .line1("123 Main St")
                .line2("Apt 4B")
                .latitude(37.7749f)
                .longitude(-122.4194f)
                .creationDate(new Date())
                .activationDate(new Date())
                .lastModifiedDate(new Date())
                .state("ACT")
                .closedDate(null)
                .comments("This is an example group")
                .members(members)
                .build();
    }

    @Test
    void testObtainCompanyByGroupName() {
        when(this.groupCompanyRepository.findFirstByGroupName("Example Group")).thenReturn(this.company);
        assertDoesNotThrow(() -> {
            this.groupCompanyService.obtainCompanyByGroupName("Example Group");
        });
        assertThrows(RuntimeException.class, () -> {
            this.groupCompanyService.obtainCompanyByGroupName("Empresa");
        });
        this.company.setState("INA");
        assertThrows(RuntimeException.class, () -> {
            this.groupCompanyService.obtainCompanyByGroupName("Example Group");
        });
    }

    @Test
    void testObtainAllCompanies() {
        List<GroupCompany> companies = new ArrayList<>();
        companies.add(this.company);
        when(this.groupCompanyRepository.findAll()).thenReturn(companies);
        assertDoesNotThrow(() -> {
            this.groupCompanyService.obtainAllCompanies();
        });
        companies.clear();
        assertThrows(RuntimeException.class, () -> {
            this.groupCompanyService.obtainAllCompanies();
        });
    }

    @Test
    void testAddMember(){

    }

}
