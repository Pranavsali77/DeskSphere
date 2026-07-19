package com.ithelpdesk.backend.security;
import com.ithelpdesk.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the logged-in user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the user's role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().name())
        );
    }

    /**
     * Returns encrypted password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Username used for login
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Account expiration
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Password expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * User enabled
     */
    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
