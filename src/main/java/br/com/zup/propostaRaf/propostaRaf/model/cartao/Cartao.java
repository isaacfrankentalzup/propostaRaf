package br.com.zup.propostaRaf.propostaRaf.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {
    @Id
    private String id;
    private String titular;
    private BigDecimal limite;
    private String idProposta;
    @OneToMany
    private List<Bloqueios> bloqueios = new ArrayList<>();
    @OneToMany
    private List<Avisos> avisos = new ArrayList<>();
    @OneToMany
    private List<Carteiras> carteiras = new ArrayList<>();
    @OneToMany
    private List<Parcelas> parcelas = new ArrayList<>();
    @OneToOne
    private Renegociacao renegociacao;
    @OneToOne
    private Vencimento vencimento;


 @Deprecated
 public Cartao() {
 }

    public Cartao(String id, String titular, BigDecimal limite, String idProposta, List<Bloqueios> bloqueios, List<Avisos> avisos, List<Carteiras> carteiras, List<Parcelas> parcelas, Renegociacao renegociacao, Vencimento vencimento) {
        this.id = id;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public List<Bloqueios> getBloqueios() {
        return bloqueios;
    }

    public List<Avisos> getAvisos() {
        return avisos;
    }

    public List<Carteiras> getCarteiras() {
        return carteiras;
    }

    public List<Parcelas> getParcelas() {
        return parcelas;
    }

    public Renegociacao getRenegociacao() {
        return renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }
}
