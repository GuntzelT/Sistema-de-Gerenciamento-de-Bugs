
public class Desenvolvedor {

    private int id;
    private Bug bugAtual;
    private int tempoRestante;

    
    public Desenvolvedor(int id) {
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

    
    public void iniciarCorrecao(Bug bug, int tempo, int rodadaAtual) {

        if (!estaDisponivel()) {
            throw new IllegalStateException(
                "O desenvolvedor já está ocupado."
            );
        }

        if (bug == null) {
            throw new IllegalArgumentException(
                "O bug não pode ser nulo."
            );
        }

        if (tempo <= 0) {
            throw new IllegalArgumentException(
                "O tempo de correção deve ser positivo."
            );
        }

        this.bugAtual = bug;
        this.tempoRestante = tempo;

        bug.setTempoCorrecaoRestante(tempo);
        bug.setRodadaInicioCorrecao(rodadaAtual);
    }

    
    public void processarRodada() {

        if (estaDisponivel()) {
            return;
        }

        if (tempoRestante > 0) {
            tempoRestante--;
            bugAtual.decrementarTempoCorrecao();
        }
    }

    
    public boolean correcaoConcluida() {
        return !estaDisponivel() && tempoRestante == 0;
    }

    
    public Bug finalizarCorrecao() {

        if (!correcaoConcluida()) {
            throw new IllegalStateException(
                "A correção ainda não foi concluída."
            );
        }

        Bug bugConcluido = bugAtual;

        bugAtual = null;
        tempoRestante = 0;

        return bugConcluido;
    }

    
    public void exibirEstado() {

        if (estaDisponivel()) {
            System.out.println(
                "Desenvolvedor " + id + ": Disponível"
            );
        } else {
            System.out.println(
                "Desenvolvedor " + id
                + ": Corrigindo bug " + bugAtual.getId()
                + " | Tempo restante: " + tempoRestante
            );
        }
    }
}
