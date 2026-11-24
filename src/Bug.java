
public class Bug {

    
    private String id;

    private Prioridade prioridade;

    private int rodadaChegada;
    private int rodadaClassificacao;
    private int rodadaInicioCorrecao;

    private int tempoClassificacaoRestante;
    private int tempoCorrecaoRestante;

    public Bug(String id, int rodadaChegada) {
        this.id = id;
        this.rodadaChegada = rodadaChegada;

        this.prioridade = null;
        this.rodadaClassificacao = -1;
        this.rodadaInicioCorrecao = -1;

        this.tempoClassificacaoRestante = 0;
        this.tempoCorrecaoRestante = 0;
    }

    
    public String getId() {
        return id;
    }

    
    public Prioridade getPrioridade() {
        return prioridade;
    }

    
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    
    public int getRodadaChegada() {
        return rodadaChegada;
    }

    
    public int getRodadaClassificacao() {
        return rodadaClassificacao;
    }

    public void setRodadaClassificacao(int rodadaClassificacao) {
        this.rodadaClassificacao = rodadaClassificacao;
    }

    
    public int getTempoClassificacaoRestante() {
        return tempoClassificacaoRestante;
    }

    public void setTempoClassificacaoRestante(int tempo) {
        this.tempoClassificacaoRestante = tempo;
    }

    
    public int getTempoCorrecaoRestante() {
        return tempoCorrecaoRestante;
    }

    public void setTempoCorrecaoRestante(int tempo) {
        this.tempoCorrecaoRestante = tempo;
    }

    
    public void decrementarTempoClassificacao() {
        if (tempoClassificacaoRestante > 0) {
            tempoClassificacaoRestante--;
        }
    }

    
    public void decrementarTempoCorrecao() {
        if (tempoCorrecaoRestante > 0) {
            tempoCorrecaoRestante--;
        }
    }

    
    public int getRodadaInicioCorrecao() {
        return rodadaInicioCorrecao;
    }

    public void setRodadaInicioCorrecao(int rodadaInicioCorrecao) {
        this.rodadaInicioCorrecao = rodadaInicioCorrecao;
    }

    
    @Override
    public String toString() {
        return "Bug: " + id +
               " | Prioridade: " + prioridade +
               " | Chegada: " + rodadaChegada;
    }
}
