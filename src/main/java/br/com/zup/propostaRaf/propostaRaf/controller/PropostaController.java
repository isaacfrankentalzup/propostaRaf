package br.com.zup.propostaRaf.propostaRaf.controller;

import br.com.zup.propostaRaf.propostaRaf.controller.dto.PropostaRequest;
import br.com.zup.propostaRaf.propostaRaf.feign.CartaoFeign;
import br.com.zup.propostaRaf.propostaRaf.feign.PropostaFeign;
import br.com.zup.propostaRaf.propostaRaf.model.Proposta;
import br.com.zup.propostaRaf.propostaRaf.model.analise.DadosAnaliseRequest;
import br.com.zup.propostaRaf.propostaRaf.model.analise.DadosAnaliseResponse;
import br.com.zup.propostaRaf.propostaRaf.model.analise.PropostaStatus;
import br.com.zup.propostaRaf.propostaRaf.model.analise.ResultadoSolicitacao;
import br.com.zup.propostaRaf.propostaRaf.model.cartao.Cartao;
import br.com.zup.propostaRaf.propostaRaf.model.novoCartao.NovoCartaoDTO;
import br.com.zup.propostaRaf.propostaRaf.repository.PropostaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private PropostaFeign feign;
    private CartaoFeign cartaoFeign;

    public PropostaController(PropostaRepository propostaRepository, PropostaFeign feign, CartaoFeign cartaoFeign) {
        this.propostaRepository = propostaRepository;
        this.feign = feign;
        this.cartaoFeign = cartaoFeign;
    }

    @PostMapping
    public ResponseEntity<?> salvaProposta(@RequestBody @Valid PropostaRequest propostaRequest) {

        Boolean existsDocNoBanco = propostaRepository.existsByDocumento(propostaRequest.getDocumento());
        Boolean existsEmailNoBanco = propostaRepository.existsByEmail(propostaRequest.getEmail());

        if (existsDocNoBanco) {
            return ResponseEntity.unprocessableEntity().build(); // vai retornar 422
        }
        if (existsEmailNoBanco){
            return ResponseEntity.unprocessableEntity().build(); // vai retornar 422
        }

        Proposta proposta = propostaRequest.toProposta();

        Proposta propostaSalva = propostaRepository.save(proposta);

        // para retornar um status 201
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(propostaSalva.getId()).toUri();

        return ResponseEntity.created(uri).build();  //201 created
    }

    //public void teste(@RequestBody )

    @PostMapping("/analises") //api/v1/propostas/analises { nome, documento, idproposta }
    public Proposta analise(@RequestBody DadosAnaliseRequest dadosAnaliseRequest) {

        //primeiro verifico se proposta enviada pelo cliente existe
        Optional<Proposta> propostaAtual =
                propostaRepository.findById(Long.parseLong(dadosAnaliseRequest.getIdProposta()));

        if (propostaAtual.isPresent()) {

            //consulta a api externa
            DadosAnaliseResponse resultadoAnalise = feign.dadosParaAnalise(dadosAnaliseRequest);

            if (resultadoAnalise.getResultadoSolicitacao().equals(ResultadoSolicitacao.SEM_RESTRICAO)) {
                propostaAtual.get().setEstatus(PropostaStatus.ELEGIVEL);
            } else {
                propostaAtual.get().setEstatus(PropostaStatus.NAO_ELEGIVEL);
            }
            propostaRepository.save(propostaAtual.get());
        }
        return propostaAtual.get();
    }

    @PostMapping("/cartao/gerar/")
    @Transactional
    public void geraCartao(@RequestBody NovoCartaoDTO novoCartaoDTO) {

        //preciso saber em qual proposta devo atrelar meu cartao
        Optional<Proposta> propostaAtual = propostaRepository.findById(Long.parseLong(novoCartaoDTO.getIdProposta()));

        if (propostaAtual.isPresent()) {

            // se existir, cria um novo cartão
            Cartao cartao = cartaoFeign.getCartao(novoCartaoDTO);

            if (cartao != null) {

                //variavel para pegar o id do cartão e 0 numero do cartão
                String numerCartao = cartao.getId();  // o meu é long. Da aula é String

                // pega a proposta e seta um novo numero de cartao
                propostaAtual.get().setNumerCartao(numerCartao);
            }
        }
            }

    @GetMapping("/acompanhamento/{idProposta}")
    public ResponseEntity<Proposta> consultaProposta(@PathVariable String idProposta){
        Optional<Proposta> temProposta = propostaRepository.findById(Long.parseLong(idProposta));

        if(temProposta.isPresent()){
            return ResponseEntity.ok(temProposta.get());
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/listaproposta")
    public List<Proposta> listAll(){
        return propostaRepository.findAll();
    }

}
