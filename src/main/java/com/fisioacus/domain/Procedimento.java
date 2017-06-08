package com.fisioacus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Procedimento.
 */
@Entity
@Table(name = "procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Procedimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "cid", nullable = false)
    private String cid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Procedimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuration() {
        return duration;
    }

    public Procedimento duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCid() {
        return cid;
    }

    public Procedimento cid(String cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Procedimento procedimento = (Procedimento) o;
        if (procedimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Procedimento{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", duration='" + getDuration() + "'" +
            ", cid='" + getCid() + "'" +
            "}";
    }
}
