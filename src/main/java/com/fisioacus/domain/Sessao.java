package com.fisioacus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Sessao.
 */
@Entity
@Table(name = "sessao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dtInicio;

    @ManyToOne
    private Profissional profissional;

    @ManyToOne
    private Atendimento atendimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Sessao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public Sessao dtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
        return this;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Sessao profissional(Profissional profissional) {
        this.profissional = profissional;
        return this;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public Sessao atendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        return this;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sessao sessao = (Sessao) o;
        if (sessao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sessao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dtInicio='" + getDtInicio() + "'" +
            "}";
    }
}
