package com.aceballos.reservas_canchas.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aceballos.reservas_canchas.entities.Usuario;
import com.aceballos.reservas_canchas.exceptions.NoEncontradoException;
import com.aceballos.reservas_canchas.repositories.IUsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isEmpty()) {
            throw new NoEncontradoException(email);
        }

        Usuario usuario = usuarioOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());

        return new User(usuario.getEmail(),
                        usuario.getClave(),
                        usuario.isUsuarioHabilitado(),
                        true,
                        true,
                        true,
                        authorities);
    }
}
