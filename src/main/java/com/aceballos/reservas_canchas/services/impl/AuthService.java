package com.aceballos.reservas_canchas.services.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.aceballos.reservas_canchas.dtos.LoginDto;
import com.aceballos.reservas_canchas.entities.Usuario;
import com.aceballos.reservas_canchas.exceptions.CredencialesInvalidasException;
import com.aceballos.reservas_canchas.exceptions.YaExisteException;
import com.aceballos.reservas_canchas.repositories.IUsuarioRepository;
import com.aceballos.reservas_canchas.services.IAuthService;
import com.aceballos.reservas_canchas.services.IUsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static com.aceballos.reservas_canchas.configuration.TokenJwtConfig.*;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public Map<String, String> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getClave())
            );

        User usuario = (User) authentication.getPrincipal();
        String email = usuario.getUsername();
        Usuario usuarioDb = usuarioRepository.findByEmail(email).get();
        String nombre = "%s %s".formatted(usuarioDb.getNombre(), usuarioDb.getApellido());

        Collection<? extends GrantedAuthority> roles = usuario.getAuthorities();

        Claims claims;
        try {
            claims = Jwts.claims()
                            .add("authorities", new ObjectMapper().writeValueAsString(roles))
                            .add("email", email)
                            .add("name", nombre)
                            .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }

        String token = Jwts.builder()
                            .subject(email)
                            .claims(claims)
                            .expiration(new Date(System.currentTimeMillis() + 3600000))
                            .signWith(SECRET_KEY)
                            .compact();

        return Map.of(
            "token", token,
            "email", email,
            "nombre", nombre,
            "mensaje", "Has iniciado sesión con éxito"
            );
        } catch (BadCredentialsException e) {
            throw new CredencialesInvalidasException();
        }
    }

    @Override
    public Usuario registro(Usuario usuario) {
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new YaExisteException("email", usuario.getEmail());
        }

        return usuarioService.crearUsuario(usuario);
    }

}
