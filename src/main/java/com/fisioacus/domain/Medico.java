package com.fisioacus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Médico.
 */
@Entity
@Table(name = "medico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "crm")
    private String crm;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "contato")
    private String contato;

    @ManyToOne
    @JoinColumn
    private Especialidade especialidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Medico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public Medico crm(String crm) {
        this.crm = crm;
        return this;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getTelefone() {
        return telefone;
    }

    public Medico telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getContato() {
        return contato;
    }

    public Medico contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Medico especialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medico medico = (Medico) o;
        if (medico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Médico{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", crm='" + getCrm() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", contato='" + getContato() + "'" +
            "}";
    }
}
