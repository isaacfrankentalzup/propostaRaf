package br.com.zup.propostaRaf.propostaRaf.feign;

import br.com.zup.propostaRaf.propostaRaf.model.cartao.Cartao;
import br.com.zup.propostaRaf.propostaRaf.model.novoCartao.NovoCartaoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//HTTP:LOCALHOST:8888

@FeignClient(url = "${proposta.cartao}", name = "cartao")
public interface CartaoFeign {

    @PostMapping("/api/cartoes/")
    public Cartao getCartao(NovoCartaoDTO novoCartaoDTO);

    @PostMapping("api/cartoes/{id}/bloqueios")
    public String bloqueiaCartao(@PathVariable String id,
                                 @RequestBody String sistemaResponsavel);

    }




