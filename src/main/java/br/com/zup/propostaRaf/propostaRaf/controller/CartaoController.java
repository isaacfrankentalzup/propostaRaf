package br.com.zup.propostaRaf.propostaRaf.controller;

import br.com.zup.propostaRaf.propostaRaf.feign.CartaoFeign;
import br.com.zup.propostaRaf.propostaRaf.model.biometria.Biometria;
import br.com.zup.propostaRaf.propostaRaf.model.cartao.Bloqueios;
import br.com.zup.propostaRaf.propostaRaf.model.cartao.Cartao;
import br.com.zup.propostaRaf.propostaRaf.repository.BiometriaRepository;
import br.com.zup.propostaRaf.propostaRaf.repository.BloqueioRepository;
import br.com.zup.propostaRaf.propostaRaf.repository.CartaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("${minhaapi.cartoes}")
public class CartaoController {

    private CartaoRepository cartaoRepository;
    private BloqueioRepository bloqueioRepository;
    private BiometriaRepository biometriaRepository;
    private CartaoFeign cartaoFeign;

    public CartaoController(CartaoRepository cartaoRepository,
                            BiometriaRepository biometriaRepository,
                            CartaoFeign cartaoFeign) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
        this.cartaoFeign = cartaoFeign;
    }

    @PostMapping("/{idCartao}")
    public void salvaBiometria(@PathVariable String idCartao){
        //tenho o possível numero do cartao - VERIFICAR A EXISTENCIA DESTE CARTAO

        Optional<Cartao> temCartao = cartaoRepository.findById(idCartao);

        if(temCartao.isPresent()){

            //gerar um fingerprint com base64
            Long numeroAleatorio = new Random().nextLong();
            byte[] finger = numeroAleatorio.toString().getBytes();
            String fingerprint = Base64.getEncoder().encodeToString(finger);

            //crio uma nova biometria utilizar o finger gerando anteriormente
            Biometria novaBiometria = new Biometria(fingerprint);

            //salvar no banco a minha biometria
            Biometria biometriaSalva = biometriaRepository.save(novaBiometria);

            //atualizar meu cartao com a novabiometria
            temCartao.get().setBiometrias(biometriaSalva);

            //atualizar no banco o meu cartao com a novabiometria
            cartaoRepository.save(temCartao.get());
        }
    }
    @GetMapping
    public List<Cartao> findAll(){

        return cartaoRepository.findAll();
    }

    @PostMapping("bloqueio/{cartaoid}")
    public ResponseEntity<?> bloqueioCartao(@PathVariable String cartaoId){

        if (cartaoId.isBlank()){
            return ResponseEntity.badRequest().build();  //400
        }

        Optional<Cartao> temCartao = cartaoRepository.findById(cartaoId);

        if(temCartao.isEmpty()){
            return ResponseEntity.notFound().build();  //404
        }

        Cartao cartaoEncontrado = temCartao.get();

        //BLOQUEADO
        String respostaBloqueio = cartaoFeign.bloqueiaCartao(cartaoEncontrado.getId(),"MeuSistema");

        if(respostaBloqueio.equals("BLOQUEADO")) {
            Bloqueios novoBloqueios = new Bloqueios(
                    cartaoEncontrado.getId(),
                    "MeuSistema",
                    false);

            Bloqueios bloqueioSalvo = bloqueioRepository.save(novoBloqueios);

            cartaoEncontrado.addBloqueio(bloqueioSalvo);

            cartaoRepository.save(cartaoEncontrado);
            return ResponseEntity.ok().build(); //200
        }
            return ResponseEntity.unprocessableEntity().build(); //422
        }

    }






