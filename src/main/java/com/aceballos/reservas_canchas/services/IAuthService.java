package com.aceballos.reservas_canchas.services;

import java.util.Map;

import com.aceballos.reservas_canchas.dtos.LoginDto;
import com.aceballos.reservas_canchas.entities.Usuario;

public interface IAuthService {

    public Map<String, String> login(LoginDto loginDto);

    public Usuario registro(Usuario usuario);
}
