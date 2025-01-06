package com.aceballos.reservas_canchas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aceballos.reservas_canchas.entities.Cancha;
import com.aceballos.reservas_canchas.services.ICanchaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CanchaController {

    @Autowired
    private ICanchaService canchaService;
    
    @GetMapping("/canchas")
    public ResponseEntity<List<Cancha>> traerCanchas() {
        List<Cancha> canchas = canchaService.traerCanchas();
        return ResponseEntity.ok(canchas);
    }

    @GetMapping("/canchas-activas")
    public ResponseEntity<List<Cancha>> traerCanchasActivas() {
        List<Cancha> canchasActivas = canchaService.traerCanchasActivas();
        return ResponseEntity.ok(canchasActivas);
    }

    @GetMapping("/canchas/{id}") 
    public ResponseEntity<Cancha> traerCancha(@PathVariable Long idCancha) {
        Cancha cancha = canchaService.traerCancha(idCancha);
        return ResponseEntity.ok(cancha);
    }

    @PostMapping("/canchas")
    public ResponseEntity<Cancha> crearCancha(@RequestBody @Valid Cancha cancha) {
        Cancha canchaNueva = canchaService.crearCancha(cancha);
        return new ResponseEntity<>(canchaNueva, HttpStatus.CREATED);
    }

    @PutMapping("/canchas/{id}")
    public ResponseEntity<Cancha> modificarCancha(@PathVariable Long idCancha, @RequestBody @Valid Cancha cancha) {
        cancha.setIdCancha(idCancha);
        Cancha canchaModificada = canchaService.modificarCancha(cancha);
        return ResponseEntity.ok(canchaModificada);
    }


    @DeleteMapping("/canchas/{id}")
    public ResponseEntity<Cancha> borrarCancha(@PathVariable Long idCancha) {
        return ResponseEntity.ok(canchaService.borrarCancha(idCancha));
    }
}
