package br.com.zup.propostaRaf.propostaRaf.model.analise;

public enum ResultadoSolicitacao {
    COM_RESTRICAO,SEM_RESTRICAO;

    public PropostaStatus Convert(){
        if(this.equals(SEM_RESTRICAO)){
            return PropostaStatus.ELEGIVEL;
        }else{
            return PropostaStatus.NAO_ELEGIVEL;
        }

    }
}
