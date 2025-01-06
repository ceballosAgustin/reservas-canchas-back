package com.aceballos.reservas_canchas.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aceballos.reservas_canchas.dtos.LoginDto;
import com.aceballos.reservas_canchas.entities.Usuario;
import com.aceballos.reservas_canchas.services.IAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        Map<String, String> response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioNuevo = authService.registro(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", "Usuario creado con éxito",
            "nombre", usuarioNuevo.getNombre()
            ));
    }
}
