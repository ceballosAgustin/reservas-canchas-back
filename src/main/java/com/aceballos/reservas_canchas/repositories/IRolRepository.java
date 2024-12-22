package com.aceballos.reservas_canchas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aceballos.reservas_canchas.entities.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long>{
    
    public Optional<Rol> findByNombre(String nombre);

}
