package ec.edu.espe.arquitectura.banquito.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.arquitectura.banquito.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    Client findFirstByTypeDocumentIdAndDocumentId(String typeDocumentId, String documentId);

    List<Client> findByLastNameLike(String lastName);
    Optional<Client> findFirstByEmailAddress(String emailAddress);

    Client findFirstByUniqueKey(String uniqueKey);

}
