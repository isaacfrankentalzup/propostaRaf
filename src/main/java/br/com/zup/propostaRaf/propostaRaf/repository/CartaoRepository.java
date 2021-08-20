package br.com.zup.propostaRaf.propostaRaf.repository;

import br.com.zup.propostaRaf.propostaRaf.model.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

}
