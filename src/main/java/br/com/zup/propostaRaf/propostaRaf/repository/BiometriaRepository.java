package br.com.zup.propostaRaf.propostaRaf.repository;

import br.com.zup.propostaRaf.propostaRaf.model.biometria.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, Long> {


}
