package ru.otus.authapp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.otus.authapp.exception.ClientExistsException;
import ru.otus.authapp.exception.InvalidPasswordException;
import ru.otus.authapp.exception.ClientNotFoundException;
import ru.otus.authapp.model.Client;
import ru.otus.authapp.model.LoginRequest;
import ru.otus.authapp.model.RegisterRequest;
import ru.otus.authapp.repository.ClientRepository;

import java.net.URI;
import java.security.KeyPair;
import java.sql.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final PasswordEncoder passwordEncoder;
    private final KeyPair keyPair;

    @Value("${client.profiles.uri}")
    private URI profilesUri;

    @Transactional
    @Override
    public Map<String, UUID> register(RegisterRequest registerRequest) {

        clientRepository.findByLogin(registerRequest.getLogin())
                .ifPresent(u -> {
                    throw new ClientExistsException("Client already exists");
                });

        final Client client = Client.builder()
                .email(registerRequest.getEmail())
                .login(registerRequest.getLogin())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        final RequestEntity<String> requestEntity = RequestEntity.post(profilesUri + "/profiles").body(registerRequest.getEmail());

        final UUID profileUUID = restTemplate.exchange(requestEntity, UUID.class).getBody();

        client.setProfileId(profileUUID);

        clientRepository.save(client);

        return Map.of("profileId", client.getProfileId(), "userId", client.getId());
    }

    @Transactional
    @Override
    public String login(LoginRequest loginRequest) {

        final Client client = clientRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
            throw new InvalidPasswordException("Password is invalid");
        }

        String jwtToken = Jwts.builder()
                .setIssuer("http://arch-auth-service.dadaev-arch-otus")
                .setSubject(client.getEmail())
                .claim("kid", "zdadaev")
                .claim("profileId", client.getProfileId())
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(new Date(System.currentTimeMillis() + 300000)) //300 sec
                .compact();

        log.info("Generated token: {}", jwtToken);

        return jwtToken;
    }

}