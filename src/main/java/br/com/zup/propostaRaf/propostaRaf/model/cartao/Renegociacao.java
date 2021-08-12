package br.com.zup.propostaRaf.propostaRaf.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Renegociacao {
    @Id
    public String id;
    public  Integer quantidade;
    public BigDecimal valor;
    public LocalDateTime dataDeCriacao;

    public Renegociacao() {
    }

    public Renegociacao(String id, Integer quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}

/*
"renegociacao": {
    "id": "string",
    "quantidade": 0,
    "valor": 0,
    "dataDeCriacao": "2021-08-14T13:22:44.051Z"
  },
 */
