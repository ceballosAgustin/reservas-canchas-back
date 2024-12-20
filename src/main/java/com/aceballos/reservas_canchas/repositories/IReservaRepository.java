package com.aceballos.reservas_canchas.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aceballos.reservas_canchas.entities.Reserva;

@Repository
public interface IReservaRepository extends JpaRepository<Long, Reserva> {

    public List<Reserva> findByCanchaIdCanchaAndFechaHoraBetween(Long idCancha, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin); 

}
