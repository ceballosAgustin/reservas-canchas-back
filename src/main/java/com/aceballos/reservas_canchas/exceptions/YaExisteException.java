package com.aceballos.reservas_canchas.exceptions;

public class YaExisteException extends RuntimeException{
    
    private static final long serialVersionUID = -2105879702918560904L;

    public YaExisteException(String campo, String valor) {
        super("El " + campo + " " + valor + "ya se encuentra registrado, intente con otro");
    }
}
