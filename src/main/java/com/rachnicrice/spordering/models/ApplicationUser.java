package com.rachnicrice.spordering.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class ApplicationUser implements UserDetails {
    //instance variables
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;

//    Default Constructor
    public ApplicationUser() {
    }

//    Actual Contructor
    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //instance methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
