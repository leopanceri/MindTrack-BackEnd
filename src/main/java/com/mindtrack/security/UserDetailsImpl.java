package com.mindtrack.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindtrack.entity.Usuario;
import com.mindtrack.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private Long id;
    @Getter
    private String name;
    private String username;
    @Getter
    private boolean status;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String name,String email, boolean status, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = email;
        this.status = status;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities;
        if(Objects.equals(usuario.getPerfil(), "Administrador")){
            authorities = List.of(new SimpleGrantedAuthority("ADM"));
        }else{
            authorities = List.of(new SimpleGrantedAuthority("FUNC"));
        }
        boolean isUserEnabled = (usuario.getStatus() == Status.ATIVO);
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                isUserEnabled,
                usuario.getSenha(),
                authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
