package ec.edu.espe.arquitectura.banquito.security;

import ec.edu.espe.arquitectura.banquito.model.Client;
import ec.edu.espe.arquitectura.banquito.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException{
        Client client= clientRepository.findFirstByEmailAddress(emailAddress).orElseThrow(
                ()->new UsernameNotFoundException("El usuario con ese correo electrónico no existe"+emailAddress)
        );
        return new UserDetailsImpl(client);
    }
}
