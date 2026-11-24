
public class Configuracao {

    
    private int capacidadeFilaEntrada;

    
    private double probabilidadeChegada;

    
    private int quantidadeAnalistas;
    private int quantidadeDesenvolvedores;

    
    private int tempoClassificacao;
    private int tempoCorrecao;

    
    private double probabilidadeCritica;
    private double probabilidadeAlta;
    private double probabilidadeMedia;
    private double probabilidadeBaixa;

    
    private int totalRodadas;

    
    private long sementeAleatoria;

    
    public Configuracao() {

        capacidadeFilaEntrada = 30;

        probabilidadeChegada = 0.50;

        quantidadeAnalistas = 2;

        tempoClassificacao = 2;

        quantidadeDesenvolvedores = 3;

        tempoCorrecao = 5;

        probabilidadeCritica = 0.10;
        probabilidadeAlta = 0.20;
        probabilidadeMedia = 0.30;
        probabilidadeBaixa = 0.40;

        totalRodadas = 150;

        sementeAleatoria = 12345;
    }

    

    public int getCapacidadeFilaEntrada() {
        return capacidadeFilaEntrada;
    }

    public double getProbabilidadeChegada() {
        return probabilidadeChegada;
    }

    public int getQuantidadeAnalistas() {
        return quantidadeAnalistas;
    }

    public int getTempoClassificacao() {
        return tempoClassificacao;
    }

    public int getQuantidadeDesenvolvedores() {
        return quantidadeDesenvolvedores;
    }

    public int getTempoCorrecao() {
        return tempoCorrecao;
    }

    public double getProbabilidadeCritica() {
        return probabilidadeCritica;
    }

    public double getProbabilidadeAlta() {
        return probabilidadeAlta;
    }

    public double getProbabilidadeMedia() {
        return probabilidadeMedia;
    }

    public double getProbabilidadeBaixa() {
        return probabilidadeBaixa;
    }

    public int getTotalRodadas() {
        return totalRodadas;
    }

    public long getSementeAleatoria() {
        return sementeAleatoria;
    }

    

    public void setCapacidadeFilaEntrada(int capacidade) {

        if (capacidade < 10 || capacidade > 50) {
            throw new IllegalArgumentException(
                "A capacidade deve estar entre 10 e 50."
            );
        }

        capacidadeFilaEntrada = capacidade;
    }

    public void setProbabilidadeChegada(double probabilidade) {

        if (probabilidade < 0.20 || probabilidade > 0.80) {
            throw new IllegalArgumentException(
                "A probabilidade de chegada deve estar entre 20% e 80%."
            );
        }

        probabilidadeChegada = probabilidade;
    }

    public void setQuantidadeAnalistas(int quantidade) {

        if (quantidade < 1 || quantidade > 3) {
            throw new IllegalArgumentException(
                "A quantidade de analistas deve estar entre 1 e 3."
            );
        }

        quantidadeAnalistas = quantidade;
    }

    public void setTempoClassificacao(int tempo) {

        if (tempo < 1 || tempo > 3) {
            throw new IllegalArgumentException(
                "O tempo de classificação deve estar entre 1 e 3."
            );
        }

        tempoClassificacao = tempo;
    }

    public void setQuantidadeDesenvolvedores(int quantidade) {

        if (quantidade < 1 || quantidade > 5) {
            throw new IllegalArgumentException(
                "A quantidade de desenvolvedores deve estar entre 1 e 5."
            );
        }

        quantidadeDesenvolvedores = quantidade;
    }

    public void setTempoCorrecao(int tempo) {

        if (tempo < 2 || tempo > 10) {
            throw new IllegalArgumentException(
                "O tempo de correção deve estar entre 2 e 10."
            );
        }

        tempoCorrecao = tempo;
    }

    public void setTotalRodadas(int rodadas) {

        if (rodadas < 100 || rodadas > 200) {
            throw new IllegalArgumentException(
                "A simulação deve ter entre 100 e 200 rodadas."
            );
        }

        totalRodadas = rodadas;
    }

    public void setSementeAleatoria(long semente) {
        sementeAleatoria = semente;
    }

    
    public void setProbabilidadesPrioridade(
        double critica,
        double alta,
        double media,
        double baixa
    ) {

        if (critica < 0.05 || critica > 0.15) {
            throw new IllegalArgumentException(
                "Crítica deve estar entre 5% e 15%."
            );
        }

        if (alta < 0.15 || alta > 0.30) {
            throw new IllegalArgumentException(
                "Alta deve estar entre 15% e 30%."
            );
        }

        if (media < 0.25 || media > 0.45) {
            throw new IllegalArgumentException(
                "Média deve estar entre 25% e 45%."
            );
        }

        if (baixa < 0.30 || baixa > 0.50) {
            throw new IllegalArgumentException(
                "Baixa deve estar entre 30% e 50%."
            );
        }

        double soma = critica + alta + media + baixa;

        if (Math.abs(soma - 1.0) > 0.000001) {
            throw new IllegalArgumentException(
                "A soma das probabilidades deve ser 100%."
            );
        }

        probabilidadeCritica = critica;
        probabilidadeAlta = alta;
        probabilidadeMedia = media;
        probabilidadeBaixa = baixa;
    }

    public void exibirConfiguracao() {

        System.out.println("CONFIGURAÇÕES DA SIMULAÇÃO");

        System.out.println(
            "Capacidade da fila: " + capacidadeFilaEntrada
        );

        System.out.println(
            "Probabilidade de chegada: " +
            (probabilidadeChegada * 100) + "%"
        );

        System.out.println(
            "Quantidade de analistas: " + quantidadeAnalistas
        );

        System.out.println(
            "Tempo de classificação: " + tempoClassificacao
        );

        System.out.println(
            "Quantidade de desenvolvedores: " +
            quantidadeDesenvolvedores
        );

        System.out.println(
            "Tempo de correção: " + tempoCorrecao
        );

        System.out.println("Probabilidades de prioridade:");

        System.out.println(
            "Crítica: " + (probabilidadeCritica * 100) + "%"
        );

        System.out.println(
            "Alta: " + (probabilidadeAlta * 100) + "%"
        );

        System.out.println(
            "Média: " + (probabilidadeMedia * 100) + "%"
        );

        System.out.println(
            "Baixa: " + (probabilidadeBaixa * 100) + "%"
        );

        System.out.println("Total de rodadas: " + totalRodadas);

        System.out.println(
            "Semente aleatória: " + sementeAleatoria
        );

    }
}
