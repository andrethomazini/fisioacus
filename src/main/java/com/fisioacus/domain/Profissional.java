package com.fisioacus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Profissional.
 */
@Entity
@Table(name = "profissional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profissional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dtInicio;

    @Column(name = "dt_termino")
    private LocalDate dtTermino;

    @OneToOne
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public Profissional dtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
        return this;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtTermino() {
        return dtTermino;
    }

    public Profissional dtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
        return this;
    }

    public void setDtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Profissional pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profissional profissional = (Profissional) o;
        if (profissional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profissional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profissional{" +
            "id=" + getId() +
            ", dtInicio='" + getDtInicio() + "'" +
            ", dtTermino='" + getDtTermino() + "'" +
            "}";
    }
}
