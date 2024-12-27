package com.aceballos.reservas_canchas.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceballos.reservas_canchas.entities.Reserva;
import com.aceballos.reservas_canchas.exceptions.YaExisteException;
import com.aceballos.reservas_canchas.repositories.IReservaRepository;
import com.aceballos.reservas_canchas.services.IReservaService;

@Service
public class ReservaService implements IReservaService{

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    public List<Reserva> traerReservasPorCanchaYFecha(Long idCancha, LocalDateTime fechaHoraInicio,
            LocalDateTime fechaHoraFin) {
        return reservaRepository.findByCanchaIdCanchaAndFechaHoraBetween(idCancha, fechaHoraInicio, fechaHoraFin);
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        if(reservaRepository.findByCanchaIdCanchaAndFechaHoraBetween(
            reserva.getCancha().getIdCancha(),
            reserva.getFechaHora(), 
            reserva.getFechaHora().plusHours(1)).isEmpty()) {
        return reservaRepository.save(reserva);
        } else {
            throw new YaExisteException("fecha y hora", reserva.getFechaHora().toString());
        }
    }
}
