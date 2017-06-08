package com.fisioacus.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sessao entity.
 */
public class SessaoDTO implements Serializable {

    private Long id;

    private String descricao;

    @NotNull
    private LocalDate dtInicio;

    private Long profissionalId;

    private Long atendimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Long atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessaoDTO sessaoDTO = (SessaoDTO) o;
        if(sessaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dtInicio='" + getDtInicio() + "'" +
            "}";
    }
}
