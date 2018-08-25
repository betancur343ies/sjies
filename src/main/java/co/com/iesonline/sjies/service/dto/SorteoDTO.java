package co.com.iesonline.sjies.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import co.com.iesonline.sjies.domain.enumeration.Juegos;
import co.com.iesonline.sjies.domain.enumeration.Estado;

/**
 * A DTO for the Sorteo entity.
 */
public class SorteoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private Juegos tipo;

    @NotNull
    private ZonedDateTime fechaCreacion;

    @NotNull
    private Estado estado;

    @NotNull
    private ZonedDateTime fechaRealizacion;

    private String ganador;

    private Long operadorId;

    private String operadorNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Juegos getTipo() {
        return tipo;
    }

    public void setTipo(Juegos tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(ZonedDateTime fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public Long getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(Long operadorId) {
        this.operadorId = operadorId;
    }

    public String getOperadorNombre() {
        return operadorNombre;
    }

    public void setOperadorNombre(String operadorNombre) {
        this.operadorNombre = operadorNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SorteoDTO sorteoDTO = (SorteoDTO) o;
        if (sorteoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sorteoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SorteoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaRealizacion='" + getFechaRealizacion() + "'" +
            ", ganador='" + getGanador() + "'" +
            ", operadorId=" + getOperadorId() +
            ", operadorNombre='" + getOperadorNombre() + "'" +
            "}";
    }
}
