package com.aceballos.reservas_canchas.exceptions;

public class NoEncontradoException extends RuntimeException{
    
    private static final long serialVersionUID = -7351212460634729652L;

    public NoEncontradoException(String email) {
        super("El recurso con email " + email + " no se ha encontrado");
    }

    public NoEncontradoException(Long id) {
        super("El recurso con id " + id + " no se ha encontrado");
    }
}
