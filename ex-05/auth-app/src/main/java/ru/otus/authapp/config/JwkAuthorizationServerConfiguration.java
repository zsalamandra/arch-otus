package ru.otus.authapp.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwkAuthorizationServerConfiguration {

    private static final String KEY_STORE_FILE = "jwt-zdadaev.jks";
    private static final String KEY_STORE_PASSWORD = "zdadaev";
    private static final String KEY_ALIAS = "ztoken";
    private static final String JWK_KID = "zdadaev";

    @Bean
    public KeyPair keyPair() throws Exception {
        ClassPathResource resource = new ClassPathResource(KEY_STORE_FILE);
        InputStream is = resource.getInputStream();

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, KEY_STORE_PASSWORD.toCharArray());

        final PrivateKey key = (PrivateKey) keystore.getKey(KEY_ALIAS,
                "zdadaev".toCharArray());
        final PublicKey publicKey = keystore.getCertificate(KEY_ALIAS).getPublicKey();

        return new KeyPair(publicKey, key);
    }

    @Bean
    public JWKSet jwkSet() throws Exception {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(JWK_KID);
        return new JWKSet(builder.build());
    }
}
