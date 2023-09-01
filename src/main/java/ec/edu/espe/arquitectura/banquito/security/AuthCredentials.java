package ec.edu.espe.arquitectura.banquito.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String emailAddress;
    private String password;

}
