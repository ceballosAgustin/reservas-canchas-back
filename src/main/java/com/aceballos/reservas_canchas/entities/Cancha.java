package com.aceballos.reservas_canchas.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "canchas")
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancha")
    private Long idCancha;

    @Column(nullable = false)
    @NotBlank(message = "El nombre de la cancha no puede estar vacío")
    @Length(min = 2, max = 20)
    private String nombre;

    private Boolean canchaActiva;

    @PrePersist
    public void prePersist() {
        this.canchaActiva = true;
    }
}
