package com.dimevision.redmadrobots.service.user.userdetails;

import com.dimevision.redmadrobots.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Dimevision
 * @version 0.1
 */

@RequiredArgsConstructor
public class UserDetailsImplementation implements UserDetails {

    private final transient User user;

    private static final Boolean IS_ACTIVE = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return IS_ACTIVE;
    }

    public static UserDetails fromUser(User user) {
        Collection<SimpleGrantedAuthority> authority = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                authority
        );
    }
}
