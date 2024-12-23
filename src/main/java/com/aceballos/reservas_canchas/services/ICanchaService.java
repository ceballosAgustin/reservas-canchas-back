package com.aceballos.reservas_canchas.services;

import java.util.List;

import com.aceballos.reservas_canchas.entities.Cancha;

public interface ICanchaService {

    public List<Cancha> traerCanchas();

    public List<Cancha> traerCanchasActivas();

    public Cancha traerCancha(Long idCancha);

    public Cancha crearCancha(Cancha cancha);

    public Cancha modificarCancha(Cancha cancha);

    public Cancha borrarCancha(Cancha cancha);
}
