package com.fisioacus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fisioacus.domain.enumeration.Genero;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @NotNull
    @Column(name = "celular", nullable = false)
    private String celular;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Size(min = 7)
    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "contato")
    private String contato;

    @NotNull
    @Column(name = "dt_nascimento", nullable = false)
    private LocalDate dtNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Cidade cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Pessoa telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public Pessoa celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public Pessoa endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public Pessoa numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Pessoa complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public Pessoa cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getContato() {
        return contato;
    }

    public Pessoa contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public Pessoa dtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public Pessoa genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Pessoa cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        if (pessoa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", celular='" + getCelular() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", cep='" + getCep() + "'" +
            ", contato='" + getContato() + "'" +
            ", dtNascimento='" + getDtNascimento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
