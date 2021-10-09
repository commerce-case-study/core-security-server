package com.commerce.core.security.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;

public class JwtTokenConverter extends JwtAccessTokenConverter {

    private Map info;
    private JsonParser objectMapper = JsonParserFactory.create();
    private final RsaSigner signer;
    
    public JwtTokenConverter(KeyPair keyPair, Map info) {
        super();
        super.setKeyPair(keyPair);
        this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
        this.info = info;
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // Set additional info
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        
        String content;
        try {
            content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot convert access token to JSON", ex);
        }
        return JwtHelper.encode(content, this.signer).getEncoded();
    }
}
