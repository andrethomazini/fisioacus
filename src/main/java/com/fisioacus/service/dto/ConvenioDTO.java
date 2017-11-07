package com.fisioacus.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Convenio entity.
 */
public class ConvenioDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    private String contato;

    @NotNull
    private String telefone;

    private String observacao;

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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConvenioDTO convenioDTO = (ConvenioDTO) o;
        if(convenioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), convenioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConvenioDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", contato='" + getContato() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
