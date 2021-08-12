package br.com.zup.propostaRaf.propostaRaf.controller;

import br.com.zup.propostaRaf.propostaRaf.controller.dto.PropostaRequest;
import br.com.zup.propostaRaf.propostaRaf.model.Proposta;
import br.com.zup.propostaRaf.propostaRaf.repository.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<?> salvaProposta(@RequestBody @Valid PropostaRequest propostaRequest){

        Proposta proposta = propostaRequest.toProposta();

        Proposta propostaSalva =  propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(propostaSalva.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
