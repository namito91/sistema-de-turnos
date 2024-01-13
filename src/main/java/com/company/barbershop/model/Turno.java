package com.company.barbershop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY : mysql handles autoincrement
    @Column(name = "id")
    private int id;

    @Column(name = "dia")
    private String dia;

    @Column(name = "horario")
    private String horario;

    // p a t r i c i o
    @NotNull(message = "campo requerido")
    @Size(min = 4,message = "caracteres minimos : 4")
    @Size(max = 14,message = "caracteres maximos : 14")
    @Column(name = "nombre-usuario")
    private String nombreUsuario = "";

    @NotNull(message = "campo requerido")
    @Pattern(regexp = "341\\d{7}", message = "El número de teléfono debe tener el formato 341-5-555555")
    @Column(name = "numero-contacto")
    private String numeroContacto;

    public Turno(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", dia='" + dia + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
