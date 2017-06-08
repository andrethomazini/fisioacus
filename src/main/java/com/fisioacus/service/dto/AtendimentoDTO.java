package com.fisioacus.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Atendimento entity.
 */
public class AtendimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String medico;

    @NotNull
    private String numeroCartao;

    private Boolean desconto;

    private Float valor;

    private String queixaPrincipal;

    private String hipoteseDiagnostica;

    private LocalDate dtInicio;

    private String numeroAutenticador;

    private LocalDate dtTermino;

    private Integer quantidadeSessoes;

    private Long convenioId;

    private String convenioDescricao;

    private Long pessoaId;

    private String pessoaNome;

    private Long procedimentoId;

    private String procedimentoDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Boolean isDesconto() {
        return desconto;
    }

    public void setDesconto(Boolean desconto) {
        this.desconto = desconto;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getHipoteseDiagnostica() {
        return hipoteseDiagnostica;
    }

    public void setHipoteseDiagnostica(String hipoteseDiagnostica) {
        this.hipoteseDiagnostica = hipoteseDiagnostica;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getNumeroAutenticador() {
        return numeroAutenticador;
    }

    public void setNumeroAutenticador(String numeroAutenticador) {
        this.numeroAutenticador = numeroAutenticador;
    }

    public LocalDate getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Integer getQuantidadeSessoes() {
        return quantidadeSessoes;
    }

    public void setQuantidadeSessoes(Integer quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
    }

    public Long getConvenioId() {
        return convenioId;
    }

    public void setConvenioId(Long convenioId) {
        this.convenioId = convenioId;
    }

    public String getConvenioDescricao() {
        return convenioDescricao;
    }

    public void setConvenioDescricao(String convenioDescricao) {
        this.convenioDescricao = convenioDescricao;
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

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public String getProcedimentoDescricao() {
        return procedimentoDescricao;
    }

    public void setProcedimentoDescricao(String procedimentoDescricao) {
        this.procedimentoDescricao = procedimentoDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AtendimentoDTO atendimentoDTO = (AtendimentoDTO) o;
        if(atendimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atendimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtendimentoDTO{" +
            "id=" + getId() +
            ", medico='" + getMedico() + "'" +
            ", numeroCartao='" + getNumeroCartao() + "'" +
            ", desconto='" + isDesconto() + "'" +
            ", valor='" + getValor() + "'" +
            ", queixaPrincipal='" + getQueixaPrincipal() + "'" +
            ", hipoteseDiagnostica='" + getHipoteseDiagnostica() + "'" +
            ", dtInicio='" + getDtInicio() + "'" +
            ", numeroAutenticador='" + getNumeroAutenticador() + "'" +
            ", dtTermino='" + getDtTermino() + "'" +
            ", quantidadeSessoes='" + getQuantidadeSessoes() + "'" +
            "}";
    }
}
