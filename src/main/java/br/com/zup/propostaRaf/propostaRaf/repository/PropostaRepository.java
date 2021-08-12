package br.com.zup.propostaRaf.propostaRaf.repository;

import br.com.zup.propostaRaf.propostaRaf.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Boolean existsByDocumento(String documento);
    Boolean existsByEmail(String email);

}
