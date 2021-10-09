package com.commerce.core.security.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

@Configuration
public class JwtKeyConfig {
    
    @Value("${finbox.keystore.password}")
    private String keystorePassword;
    
    @Value("${finbox.keystore.alias}")
    private String keystoreAlias;
    
    @Value("${finbox.keystore.file}")
    private String keystoreFile;
    
    @Value("${finbox.keystore.id}")
    private String keystoreId;
    
    /**
     * Generate Key Pair, based on Generated Key Store File
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        ClassPathResource ksFile = new ClassPathResource(keystoreFile);
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, keystorePassword.toCharArray());
        return ksFactory.getKeyPair(keystoreAlias);
    }
    
    /**
     * Wrap Key Set, based on Key Pair
     * @return
     */
    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                                    .keyUse(KeyUse.SIGNATURE)
                                    .algorithm(JWSAlgorithm.RS256)
                                    .keyID(keystoreId);
        return new JWKSet(builder.build());
    }
    
    /**
     * Enhance Token Information by using Token Access Converter
     * Add Issuer Information
     * @return
     */
    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        return new JwtTokenConverter(keyPair(), Collections.singletonMap("iss", keystoreAlias));
    }
    
    /**
     * Register Token Store based on Enhanced Token Converter 
     * @return
     */
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }
}
