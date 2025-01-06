package com.aceballos.reservas_canchas.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceballos.reservas_canchas.entities.Cancha;
import com.aceballos.reservas_canchas.exceptions.NoEncontradoException;
import com.aceballos.reservas_canchas.exceptions.YaExisteException;
import com.aceballos.reservas_canchas.repositories.ICanchaRepository;
import com.aceballos.reservas_canchas.services.ICanchaService;

import jakarta.transaction.Transactional;

@Service
public class CanchaService implements ICanchaService{

    @Autowired
    private ICanchaRepository canchaRepository;
    
    @Override
    @Transactional
    public List<Cancha> traerCanchas() {
        return canchaRepository.findAll();
    }

    @Override
    public List<Cancha> traerCanchasActivas() {
        return canchaRepository.findByCanchaActivaTrue();
    }

    @Override
    public Cancha traerCancha(Long idCancha) {
        Optional<Cancha> canchaOptional = canchaRepository.findById(idCancha);

        if(canchaOptional.isEmpty()) {
            throw new NoEncontradoException(idCancha);
        }

        return canchaOptional.get();
    }

    @Override
    @Transactional
    public Cancha crearCancha(Cancha cancha) {
        cancha.setNombre(cancha.getNombre().trim());

        if(canchaRepository.existsByNombre(cancha.getNombre())) {
            throw new YaExisteException("nombre", cancha.getNombre());
        }

        return canchaRepository.save(cancha);
    }

    @Override
    @Transactional
    public Cancha modificarCancha(Cancha cancha) {
        Optional<Cancha> canchaOptional = canchaRepository.findById(cancha.getIdCancha());

        if(!canchaOptional.isPresent()) {
            throw new NoEncontradoException(cancha.getIdCancha());
        }

        Cancha canchaModificar = canchaOptional.orElseThrow();
        canchaModificar.setNombre(cancha.getNombre());
        canchaModificar.setCanchaActiva(cancha.getCanchaActiva());

        return Optional.of(canchaRepository.save(canchaModificar)).orElseThrow();
    }

    @Override
    @Transactional
    public Cancha borrarCancha(Long idCancha) {
        Cancha cancha = traerCancha(idCancha);

        if(cancha == null) {
            throw new NoEncontradoException(idCancha);
        }

        cancha.setCanchaActiva(false);

        return canchaRepository.save(cancha);
    }

}
