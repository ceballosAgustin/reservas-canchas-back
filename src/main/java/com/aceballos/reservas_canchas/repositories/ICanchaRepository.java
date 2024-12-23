package com.aceballos.reservas_canchas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aceballos.reservas_canchas.entities.Cancha;

@Repository
public interface ICanchaRepository extends JpaRepository<Cancha, Long> {

    public List<Cancha> findByCanchaActivaTrue();

    public boolean existsByNombre(String nombre);
}
