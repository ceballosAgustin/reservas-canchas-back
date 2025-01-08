package com.aceballos.reservas_canchas.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aceballos.reservas_canchas.entities.Reserva;
import com.aceballos.reservas_canchas.services.IReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping("/reservas/{id}")
    public ResponseEntity<List<Reserva>> traerReservasPorCanchaYFecha(
        @PathVariable Long id, 
        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime fechaHoraInicio, 
        @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime fechaHoraFin) {
        
        List<Reserva> reservas = reservaService.traerReservasPorCanchaYFecha(id, fechaHoraInicio, fechaHoraFin);
        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/reservas")
    public ResponseEntity<Reserva> crearReserva(@RequestBody @Valid Reserva reserva) {
        Reserva reservaNueva = reservaService.crearReserva(reserva);
        return new ResponseEntity<>(reservaNueva, HttpStatus.CREATED);
    }

}
