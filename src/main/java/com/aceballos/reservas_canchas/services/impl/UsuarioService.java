package com.aceballos.reservas_canchas.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aceballos.reservas_canchas.dtos.UsuarioDto;
import com.aceballos.reservas_canchas.entities.Rol;
import com.aceballos.reservas_canchas.entities.Usuario;
import com.aceballos.reservas_canchas.exceptions.NoEncontradoException;
import com.aceballos.reservas_canchas.exceptions.YaExisteException;
import com.aceballos.reservas_canchas.repositories.IRolRepository;
import com.aceballos.reservas_canchas.repositories.IUsuarioRepository;
import com.aceballos.reservas_canchas.services.IUsuarioService;

import jakarta.transaction.Transactional;


public class UsuarioService implements IUsuarioService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if(usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new YaExisteException("email", usuario.getEmail());
        }

        usuario.setClave(passwordEncoder.encode(usuario.getClave()));

        Optional<Rol> rolOptional = rolRepository.findByNombre("ROLE_USUARIO");
        List<Rol> roles = new ArrayList<>();

        rolOptional.ifPresent(roles::add);

        if(usuario.isAdmin()) {
            Optional<Rol> rolAdministradorOptional = rolRepository.findByNombre("ROLE_ADMINISTRADOR");
            rolAdministradorOptional.ifPresent(roles::add);
        }

        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDto findByEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        
        if(usuarioOptional.isEmpty()) {
            throw new NoEncontradoException(email);
        }

        Usuario usuario = usuarioOptional.get();

        return new UsuarioDto(usuario.getNombre(), usuario.getApellido());
    }

    @Override
    public Optional<Usuario> borrarUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if(usuarioOptional.isEmpty()) {
            throw new NoEncontradoException(idUsuario);
        }

        usuarioRepository.deleteById(idUsuario);

        return usuarioOptional;
    }

}
