package io.github.huafoog.fir.auth.component;

import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Component;

@Component
public class FirAuthorizationCodeServices implements AuthorizationCodeServices {
    @Override
    public String createAuthorizationCode(OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String s) throws InvalidGrantException {
        return null;
    }
}
