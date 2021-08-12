package br.com.zup.propostaRaf.propostaRaf.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Vencimento {
    @Id
    public String id;
    public Integer dia;
    public LocalDateTime localDateTime;

    public Vencimento() {
    }

    public Vencimento(String id, Integer dia, LocalDateTime localDateTime) {
        this.id = id;
        this.dia = dia;
        this.localDateTime = localDateTime;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}

/*
"vencimento": {
    "id": "string",
    "dia": 0,
    "dataDeCriacao": "2021-08-14T13:22:44.051Z"
  },
 */
