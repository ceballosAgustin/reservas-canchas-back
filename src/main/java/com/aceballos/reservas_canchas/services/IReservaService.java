package com.aceballos.reservas_canchas.services;

import java.time.LocalDateTime;
import java.util.List;

import com.aceballos.reservas_canchas.entities.Reserva;

public interface IReservaService {

    public List<Reserva> traerReservasPorCanchaYFecha(Long idCancha, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

    public Reserva crearReserva(Reserva reserva);
}
