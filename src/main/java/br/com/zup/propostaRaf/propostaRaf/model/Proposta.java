package br.com.zup.propostaRaf.propostaRaf.model;

import br.com.zup.propostaRaf.propostaRaf.model.analise.PropostaStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String documento;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String endereco;

        @Column(nullable = false)
        private BigDecimal salario;

        private PropostaStatus estatus = PropostaStatus.ANALISE;

        private String numerCartao;

    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
            this.documento = documento;
            this.email = email;
            this.nome = nome;
            this.endereco = endereco;
            this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getNumerCartao() { return numerCartao;}

    public PropostaStatus getEstatus() {
        return estatus;
    }

    public void setEstatus(PropostaStatus estatus){
        this.estatus = estatus;
    }

    public void setNumerCartao(String numerCartao){
        this.numerCartao = numerCartao;
    }
}

//010 010.nao_pode_haver_proposta.md

