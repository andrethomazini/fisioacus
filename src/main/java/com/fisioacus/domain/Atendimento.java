package com.fisioacus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Atendimento.
 */
@Entity
@Table(name = "atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @Column(name = "desconto")
    private Boolean desconto;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "queixa_principal")
    private String queixaPrincipal;

    @Column(name = "hipotese_diagnostica")
    private String hipoteseDiagnostica;

    @Column(name = "dt_inicio")
    private LocalDate dtInicio;

    @Column(name = "numero_autenticador")
    private String numeroAutenticador;

    @Column(name = "dt_termino")
    private LocalDate dtTermino;

    @Column(name = "quantidade_sessoes")
    private Integer quantidadeSessoes;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "cid")
    private String cid;

    @OneToOne
    @JoinColumn(unique = true)
    private Convenio convenio;

    @OneToOne
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToOne
    @JoinColumn(unique = true)
    private Procedimento procedimento;

    @OneToOne
    @JoinColumn(unique = true)
    private Medico medico;

    @OneToMany(mappedBy = "atendimento")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sessao> sessaos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public Atendimento numeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
        return this;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Boolean isDesconto() {
        return desconto;
    }

    public Atendimento desconto(Boolean desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Boolean desconto) {
        this.desconto = desconto;
    }

    public Float getValor() {
        return valor;
    }

    public Atendimento valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public Atendimento queixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
        return this;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getHipoteseDiagnostica() {
        return hipoteseDiagnostica;
    }

    public Atendimento hipoteseDiagnostica(String hipoteseDiagnostica) {
        this.hipoteseDiagnostica = hipoteseDiagnostica;
        return this;
    }

    public void setHipoteseDiagnostica(String hipoteseDiagnostica) {
        this.hipoteseDiagnostica = hipoteseDiagnostica;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public Atendimento dtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
        return this;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getNumeroAutenticador() {
        return numeroAutenticador;
    }

    public Atendimento numeroAutenticador(String numeroAutenticador) {
        this.numeroAutenticador = numeroAutenticador;
        return this;
    }

    public void setNumeroAutenticador(String numeroAutenticador) {
        this.numeroAutenticador = numeroAutenticador;
    }

    public LocalDate getDtTermino() {
        return dtTermino;
    }

    public Atendimento dtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
        return this;
    }

    public void setDtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Integer getQuantidadeSessoes() {
        return quantidadeSessoes;
    }

    public Atendimento quantidadeSessoes(Integer quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
        return this;
    }

    public void setQuantidadeSessoes(Integer quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
    }

    public String getObservacao() {
        return observacao;
    }

    public Atendimento observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCid() {
        return cid;
    }

    public Atendimento cid(String cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Atendimento convenio(Convenio convenio) {
        this.convenio = convenio;
        return this;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Atendimento pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Atendimento procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Medico getMedico() {
        return medico;
    }

    public Atendimento medico(Medico medico) {
        this.medico = medico;
        return this;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Set<Sessao> getSessaos() {
        return sessaos;
    }

    public Atendimento sessaos(Set<Sessao> sessaos) {
        this.sessaos = sessaos;
        return this;
    }

    public Atendimento addSessao(Sessao sessao) {
        this.sessaos.add(sessao);
        sessao.setAtendimento(this);
        return this;
    }

    public Atendimento removeSessao(Sessao sessao) {
        this.sessaos.remove(sessao);
        sessao.setAtendimento(null);
        return this;
    }

    public void setSessaos(Set<Sessao> sessaos) {
        this.sessaos = sessaos;
    }

    @Transient
    public Integer getSessoesRestantes() {
        Integer saldo = quantidadeSessoes != null ? quantidadeSessoes : 0;
        saldo -= getSessaos() != null ? getSessaos().size() : 0;

        return saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Atendimento atendimento = (Atendimento) o;
        if (atendimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atendimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Atendimento{" +
            "id=" + getId() +
            ", numeroCartao='" + getNumeroCartao() + "'" +
            ", desconto='" + isDesconto() + "'" +
            ", valor='" + getValor() + "'" +
            ", queixaPrincipal='" + getQueixaPrincipal() + "'" +
            ", hipoteseDiagnostica='" + getHipoteseDiagnostica() + "'" +
            ", dtInicio='" + getDtInicio() + "'" +
            ", numeroAutenticador='" + getNumeroAutenticador() + "'" +
            ", dtTermino='" + getDtTermino() + "'" +
            ", quantidadeSessoes='" + getQuantidadeSessoes() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", cid='" + getCid() + "'" +
            "}";
    }
}
