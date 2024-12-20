package com.aceballos.reservas_canchas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aceballos.reservas_canchas.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Long, Usuario>{
    
    public Optional<Usuario> findByEmail(String email);

    public Usuario findByNombre (String nombre);

    public boolean existsByEmail(String email);
}
