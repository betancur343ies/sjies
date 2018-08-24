package co.com.iesonline.sjies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Operador.
 */
@Entity
@Table(name = "operador")
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private ZonedDateTime fechaCreacion;

    @Column(name = "total_sorteos")
    private Integer totalSorteos;

    @Column(name = "sorteos_activos")
    private Integer sorteosActivos;

    @OneToMany(mappedBy = "operador")
    private Set<Sorteo> sorteos = new HashSet<>();

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

    public Operador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Operador fechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getTotalSorteos() {
        return totalSorteos;
    }

    public Operador totalSorteos(Integer totalSorteos) {
        this.totalSorteos = totalSorteos;
        return this;
    }

    public void setTotalSorteos(Integer totalSorteos) {
        this.totalSorteos = totalSorteos;
    }

    public Integer getSorteosActivos() {
        return sorteosActivos;
    }

    public Operador sorteosActivos(Integer sorteosActivos) {
        this.sorteosActivos = sorteosActivos;
        return this;
    }

    public void setSorteosActivos(Integer sorteosActivos) {
        this.sorteosActivos = sorteosActivos;
    }

    public Set<Sorteo> getSorteos() {
        return sorteos;
    }

    public Operador sorteos(Set<Sorteo> sorteos) {
        this.sorteos = sorteos;
        return this;
    }

    public Operador addSorteo(Sorteo sorteo) {
        this.sorteos.add(sorteo);
        sorteo.setOperador(this);
        return this;
    }

    public Operador removeSorteo(Sorteo sorteo) {
        this.sorteos.remove(sorteo);
        sorteo.setOperador(null);
        return this;
    }

    public void setSorteos(Set<Sorteo> sorteos) {
        this.sorteos = sorteos;
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
        Operador operador = (Operador) o;
        if (operador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", totalSorteos=" + getTotalSorteos() +
            ", sorteosActivos=" + getSorteosActivos() +
            "}";
    }
}
