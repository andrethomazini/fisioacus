package com.fisioacus.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Profissional entity.
 */
public class ProfissionalDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dtInicio;

    private LocalDate dtTermino;

    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfissionalDTO profissionalDTO = (ProfissionalDTO) o;
        if(profissionalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profissionalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfissionalDTO{" +
            "id=" + getId() +
            ", dtInicio='" + getDtInicio() + "'" +
            ", dtTermino='" + getDtTermino() + "'" +
            "}";
    }
}
