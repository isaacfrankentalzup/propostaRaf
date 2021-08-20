package br.com.zup.propostaRaf.propostaRaf.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Bloqueios {
    @Id
    private String id;
    private LocalDateTime bloqueadoEm = LocalDateTime.now();
    private String sistemaResponse;
    private Boolean ativo;

    @Deprecated
    public Bloqueios() {
    }

    public Bloqueios(String id, String sistemaResponse, Boolean ativo) {
        this.id = id;
        this.sistemaResponse = sistemaResponse;
        this.ativo = ativo;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueiadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponse() {
        return sistemaResponse;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
