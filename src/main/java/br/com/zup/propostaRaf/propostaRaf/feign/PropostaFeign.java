package br.com.zup.propostaRaf.propostaRaf.feign;

import br.com.zup.propostaRaf.propostaRaf.model.analise.DadosAnaliseRequest;
import br.com.zup.propostaRaf.propostaRaf.model.analise.DadosAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${proposta.analise}", name = "solicitacao")
public interface PropostaFeign {

    @PostMapping
    public DadosAnaliseResponse dadosParaAnalise(DadosAnaliseRequest dadosAnaliseRequest);

}
