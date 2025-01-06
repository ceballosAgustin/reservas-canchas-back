package com.aceballos.reservas_canchas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aceballos.reservas_canchas.dtos.UsuarioDto;
import com.aceballos.reservas_canchas.entities.Usuario;
import com.aceballos.reservas_canchas.services.IUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/usuarios")
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(usuarioNuevo, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable String email) {
        UsuarioDto usuarioDto = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuarioDto);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.borrarUsuario(id);
        return ResponseEntity.ok(usuarioOptional.orElseThrow());
    }
}
