package io.github.huafoog.fir.security.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author DELL
 */
public class FirUser extends User {

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String clientId;

    public FirUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
