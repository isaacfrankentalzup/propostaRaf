package br.com.zup.propostaRaf.propostaRaf.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String documento;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String endereco;

        @Column(nullable = false)
        private BigDecimal salario;

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
}

//010 010.nao_pode_haver_proposta.md

