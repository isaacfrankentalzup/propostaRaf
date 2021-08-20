package br.com.zup.propostaRaf.propostaRaf.model.biometria;

import br.com.zup.propostaRaf.propostaRaf.model.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Biometria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fingerPrint;
    private LocalDateTime criadaEm = LocalDateTime.now();

    public Biometria() {
    }

    public Biometria(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

}
