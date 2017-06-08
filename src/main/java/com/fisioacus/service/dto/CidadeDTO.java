package com.fisioacus.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.fisioacus.domain.enumeration.Estado;

/**
 * A DTO for the Cidade entity.
 */
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Estado estado;

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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if(cidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
