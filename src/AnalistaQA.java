
public class AnalistaQA {

    private int id;
    private Bug bugAtual;
    private int tempoRestante;

    
    public AnalistaQA(int id) {

        this.id = id;
        this.bugAtual = null;
        this.tempoRestante = 0;
    }

    
    public int getId() {

        return id;
    }

    
    public boolean estaDisponivel() {

        return bugAtual == null;
    }

    
    public Bug getBugAtual() {

        return bugAtual;
    }

    
    public int getTempoRestante() {

        return tempoRestante;
    }

    
    public void iniciarClassificacao(Bug bug, int tempo) {

        if (!estaDisponivel()) {
            throw new IllegalStateException(
                "O analista já está ocupado."
            );
        }

        if (bug == null || tempo <= 0) {
            throw new IllegalArgumentException(
                "Bug inválido ou tempo de classificação inválido."
            );
        }

        bugAtual = bug;

        tempoRestante = tempo;

        bug.setTempoClassificacaoRestante(tempo);

        System.out.println(
            "Analista " + id +
            " iniciou a classificação do " + bug.getId()
        );
    }

    
    public void processarRodada() {

        if (estaDisponivel()) {
            return;
        }

        tempoRestante--;

        bugAtual.decrementarTempoClassificacao();
    }

    
    public boolean classificacaoConcluida() {

        return !estaDisponivel() && tempoRestante == 0;
    }

    
    public Bug finalizarClassificacao() {

        if (!classificacaoConcluida()) {
            throw new IllegalStateException(
                "A classificação ainda não terminou."
            );
        }

        Bug bugFinalizado = bugAtual;

        bugAtual = null;

        tempoRestante = 0;

        return bugFinalizado;
    }

    
    public void exibirEstado() {

        if (estaDisponivel()) {

            System.out.println(
                "Analista " + id + " está disponível."
            );

        } else {

            System.out.println(
                "Analista " + id +
                " está classificando " + bugAtual.getId() +
                " | Tempo restante: " + tempoRestante
            );
        }
    }
}
