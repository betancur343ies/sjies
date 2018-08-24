package co.com.iesonline.sjies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import co.com.iesonline.sjies.domain.enumeration.Juegos;

import co.com.iesonline.sjies.domain.enumeration.Estado;

/**
 * A Sorteo.
 */
@Entity
@Table(name = "sorteo")
public class Sorteo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Juegos tipo;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private ZonedDateTime fechaCreacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Column(name = "fecha_realizacion", nullable = false)
    private ZonedDateTime fechaRealizacion;

    @Column(name = "ganador")
    private String ganador;

    @ManyToOne
    @JsonIgnoreProperties("sorteos")
    private Operador operador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Sorteo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Juegos getTipo() {
        return tipo;
    }

    public Sorteo tipo(Juegos tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Juegos tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Sorteo fechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public Sorteo estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFechaRealizacion() {
        return fechaRealizacion;
    }

    public Sorteo fechaRealizacion(ZonedDateTime fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
        return this;
    }

    public void setFechaRealizacion(ZonedDateTime fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getGanador() {
        return ganador;
    }

    public Sorteo ganador(String ganador) {
        this.ganador = ganador;
        return this;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public Operador getOperador() {
        return operador;
    }

    public Sorteo operador(Operador operador) {
        this.operador = operador;
        return this;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sorteo sorteo = (Sorteo) o;
        if (sorteo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sorteo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sorteo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaRealizacion='" + getFechaRealizacion() + "'" +
            ", ganador='" + getGanador() + "'" +
            "}";
    }
}
