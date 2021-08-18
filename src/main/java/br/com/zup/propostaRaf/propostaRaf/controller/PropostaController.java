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
import br.com.zup.propostaRaf.propostaRaf.repository.CartaoRepository;
import br.com.zup.propostaRaf.propostaRaf.repository.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.proposta}")
public class PropostaController {

    private PropostaRepository repository;
    private CartaoRepository cartaoRepository;
    private PropostaFeign propostaFeign;
    private CartaoFeign cartaoFeign;

    public PropostaController(PropostaRepository repository, CartaoRepository cartaoRepository,
                              PropostaFeign propostaFeign, CartaoFeign cartaoFeign) {
        this.repository = repository;
        this.cartaoRepository = cartaoRepository;
        this.propostaFeign = propostaFeign;
        this.cartaoFeign = cartaoFeign;
    }

    @PostMapping
    public ResponseEntity<?> salvaProposta(@RequestBody @Valid PropostaRequest propostaRequest){

        Boolean existeDocNoBanco = repository.existsByDocumento(propostaRequest.getDocumento());

        if(existeDocNoBanco){
            return ResponseEntity.unprocessableEntity().build(); //422 unproce
        }

        Proposta proposta = propostaRequest.toProposta();
        ResponseEntity.ok(repository.save(proposta));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build(); //201 - create
    }

    @PostMapping("/analises")
    public Proposta teste(@RequestBody DadosAnaliseRequest dadosAnaliseRequest){

        //primeiro verifico se proposta enviada pelo cliente existe
        Optional<Proposta> propostaAtual =
                repository.findById(Long.parseLong(dadosAnaliseRequest.getIdProposta()));

        if(propostaAtual.isPresent()){

            //consulta a api externa
            DadosAnaliseResponse resultadoAnalise = propostaFeign.dadosParaAnalise(dadosAnaliseRequest);

            if(resultadoAnalise.getResultadoSolicitacao().equals(ResultadoSolicitacao.SEM_RESTRICAO)){
                propostaAtual.get().setEstatus(PropostaStatus.ELEGIVEL);
            }else{
                propostaAtual.get().setEstatus(PropostaStatus.NAO_ELEGIVEL);
            }
            repository.save(propostaAtual.get());

        }
        return propostaAtual.get();
    }

    @PostMapping("/cartao/gerar/")
    public void geraCartao(@RequestBody NovoCartaoDTO novoCartaoDTO){

        //saber em qual proposta eu vou atrelar meu cartao
        Optional<Proposta> propostaAtual = repository.findById(Long.parseLong(novoCartaoDTO.getIdProposta()));

        if(propostaAtual.isPresent()){

            //cria um novo cartao
            Cartao cartao =  cartaoFeign.getCartao(novoCartaoDTO);

            if(cartao != null){
                //variavel para pegar o ID do cartao que é o número do cartao
                cartaoRepository.save(cartao);

                //SALVO O CARTÃO NA BASE DE DADOS
                String numerCartao = cartao.getId();

                //INSIRO NA PROPOSTA O NÚMERO CARTAO
                propostaAtual.get().setNumerCartao(numerCartao);

                //OU SALVO A PROPOSTA COM O NUMERO CARTAO OU UTILIZO
                //SENÃO POSSO SALVAR USANDO @Transactional para que fique automático o UPDATE
                repository.save(propostaAtual.get());

            }
        }

    }

    @GetMapping("/acompanhamento/{idProposta}")
    public ResponseEntity<Proposta> consultaProposta(@PathVariable String idProposta){

        Optional<Proposta> temProposta = repository.findById(Long.parseLong(idProposta));

        if(temProposta.isPresent()){
            return ResponseEntity.ok(temProposta.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/listandotudo")
    public List<Proposta> verTudo(){

        List<Proposta> listarTudo = repository.findAll();

        return  listarTudo;

    }



}