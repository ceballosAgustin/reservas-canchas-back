package com.aceballos.reservas_canchas.services;

import java.util.Optional;

import com.aceballos.reservas_canchas.dtos.UsuarioDto;
import com.aceballos.reservas_canchas.entities.Usuario;

public interface IUsuarioService {

    public Usuario crearUsuario(Usuario usuario);

    public UsuarioDto findByEmail(String email);

    public Optional<Usuario> borrarUsuario(Long idUsuario);
}
