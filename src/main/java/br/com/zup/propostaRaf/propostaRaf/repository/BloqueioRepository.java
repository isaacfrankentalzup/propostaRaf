package br.com.zup.propostaRaf.propostaRaf.repository;

import br.com.zup.propostaRaf.propostaRaf.model.cartao.Bloqueios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueioRepository extends JpaRepository<Bloqueios, String> {
}
