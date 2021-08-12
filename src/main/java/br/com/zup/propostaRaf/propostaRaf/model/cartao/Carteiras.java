package br.com.zup.propostaRaf.propostaRaf.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Entity
public class Carteiras {
    @Id
    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    public Carteiras() {
    }

    public Carteiras(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }
}

/*
"id": "string",
      "email": "string",
      "associadaEm": "2021-08-14T13:22:44.051Z",
      "emissor": "string"
 */