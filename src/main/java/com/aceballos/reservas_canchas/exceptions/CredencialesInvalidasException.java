package com.aceballos.reservas_canchas.exceptions;

public class CredencialesInvalidasException extends RuntimeException{
    
    private static final long serialVersionUID = -7034897190745766939L;

    public CredencialesInvalidasException() {
        super("Error de autenticación: email o clave incorrectos");
    }
}
