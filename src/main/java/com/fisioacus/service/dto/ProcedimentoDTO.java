package com.fisioacus.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Procedimento entity.
 */
public class ProcedimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Integer duration;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcedimentoDTO procedimentoDTO = (ProcedimentoDTO) o;
        if(procedimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcedimentoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}
