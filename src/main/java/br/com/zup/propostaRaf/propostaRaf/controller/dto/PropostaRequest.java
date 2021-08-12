package br.com.zup.propostaRaf.propostaRaf.controller.dto;

import br.com.zup.propostaRaf.propostaRaf.model.Proposta;
import br.com.zup.propostaRaf.propostaRaf.validators.CPFCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class PropostaRequest {


    @NotBlank @CPFCNPJ
    private String documento;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @PositiveOrZero @NotNull
    private BigDecimal salario;

    public PropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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

    public Proposta toProposta(){
        return new Proposta(
                this.documento,
                this.email,
                this.nome,
                this.endereco,
                this.salario
        );
    }
}
