
public class Metricas {

    
    private int totalRecebidos;
    private int totalRecusados;
    private int totalClassificados;
    private int totalCorrigidos;

    
    private int[] classificadosPorPrioridade;
    private int[] corrigidosPorPrioridade;

    
    private int tempoTotalTriagem;
    private int quantidadeTemposTriagem;

    private int[] tempoTotalPrioridade;
    private int[] quantidadeTemposPrioridade;

    
    private int totalRodadasRegistradas;
    private int totalAnalistasOcupados;
    private int totalDesenvolvedoresOcupados;

    private int quantidadeAnalistas;
    private int quantidadeDesenvolvedores;

    
    private int[] historicoBugsAguardando;

    // Construtor
    public Metricas(int duracao,
                    int quantidadeAnalistas,
                    int quantidadeDesenvolvedores) {

        if (duracao <= 0 || quantidadeAnalistas <= 0
                || quantidadeDesenvolvedores <= 0) {
            throw new IllegalArgumentException(
                "Os valores devem ser positivos."
            );
        }

        this.totalRecebidos = 0;
        this.totalRecusados = 0;
        this.totalClassificados = 0;
        this.totalCorrigidos = 0;

        this.classificadosPorPrioridade = new int[4];
        this.corrigidosPorPrioridade = new int[4];

        this.tempoTotalTriagem = 0;
        this.quantidadeTemposTriagem = 0;

        this.tempoTotalPrioridade = new int[4];
        this.quantidadeTemposPrioridade = new int[4];

        this.totalRodadasRegistradas = 0;
        this.totalAnalistasOcupados = 0;
        this.totalDesenvolvedoresOcupados = 0;

        this.quantidadeAnalistas = quantidadeAnalistas;
        this.quantidadeDesenvolvedores = quantidadeDesenvolvedores;

        this.historicoBugsAguardando = new int[duracao];
    }

    
    private int indicePrioridade(Prioridade prioridade) {

        if (prioridade == null) {
            throw new IllegalArgumentException(
                "A prioridade não pode ser nula."
            );
        }

        switch (prioridade) {
            case CRITICA:
                return 0;

            case ALTA:
                return 1;

            case MEDIA:
                return 2;

            case BAIXA:
                return 3;

            default:
                throw new IllegalArgumentException(
                    "Prioridade inválida."
                );
        }
    }

    
    public void registrarBugRecebido() {
        totalRecebidos++;
    }

    
    public void registrarBugRecusado() {
        totalRecusados++;
    }

    
    public void registrarBugClassificado(Bug bug) {

        if (bug == null || bug.getPrioridade() == null) {
            throw new IllegalArgumentException(
                "Bug ou prioridade inválida."
            );
        }

        int indice = indicePrioridade(bug.getPrioridade());

        totalClassificados++;
        classificadosPorPrioridade[indice]++;

        
        int espera = bug.getRodadaClassificacao()
                     - bug.getRodadaChegada();

        tempoTotalTriagem += espera;
        quantidadeTemposTriagem++;
    }

    
    public void registrarBugCorrigido(Bug bug) {

        if (bug == null || bug.getPrioridade() == null) {
            throw new IllegalArgumentException(
                "Bug ou prioridade inválida."
            );
        }

        int indice = indicePrioridade(bug.getPrioridade());

        totalCorrigidos++;
        corrigidosPorPrioridade[indice]++;

        
        int espera = bug.getRodadaInicioCorrecao()
                     - bug.getRodadaClassificacao();

        tempoTotalPrioridade[indice] += espera;
        quantidadeTemposPrioridade[indice]++;
    }

    
    public void registrarBugsAguardando(
            int rodada, int quantidade) {

        if (rodada < 1 || rodada > historicoBugsAguardando.length
                || quantidade < 0) {
            throw new IllegalArgumentException(
                "Rodada ou quantidade inválida."
            );
        }

        historicoBugsAguardando[rodada - 1] = quantidade;
    }

    
    public void registrarUtilizacao(
            int analistasOcupados,
            int desenvolvedoresOcupados) {

        if (analistasOcupados < 0
                || analistasOcupados > quantidadeAnalistas
                || desenvolvedoresOcupados < 0
                || desenvolvedoresOcupados > quantidadeDesenvolvedores) {
            throw new IllegalArgumentException(
                "Quantidade de recursos ocupados inválida."
            );
        }

        totalRodadasRegistradas++;

        totalAnalistasOcupados += analistasOcupados;
        totalDesenvolvedoresOcupados += desenvolvedoresOcupados;
    }

    
    public double getTempoMedioTriagem() {

        if (quantidadeTemposTriagem == 0) {
            return 0;
        }

        return (double) tempoTotalTriagem
               / quantidadeTemposTriagem;
    }

    
    public double getTempoMedioPrioridade(Prioridade prioridade) {

        int indice = indicePrioridade(prioridade);

        if (quantidadeTemposPrioridade[indice] == 0) {
            return 0;
        }

        return (double) tempoTotalPrioridade[indice]
               / quantidadeTemposPrioridade[indice];
    }

    
    public double getUtilizacaoAnalistas() {

        if (totalRodadasRegistradas == 0) {
            return 0;
        }

        return (double) totalAnalistasOcupados
               / (totalRodadasRegistradas * quantidadeAnalistas)
               * 100;
    }

    
    public double getUtilizacaoDesenvolvedores() {

        if (totalRodadasRegistradas == 0) {
            return 0;
        }

        return (double) totalDesenvolvedoresOcupados
               / (totalRodadasRegistradas * quantidadeDesenvolvedores)
               * 100;
    }

    
    public int[] getHistoricoBugsAguardando() {
        return historicoBugsAguardando.clone();
    }

    
    public int getClassificadosPorPrioridade(
            Prioridade prioridade) {

        return classificadosPorPrioridade[
            indicePrioridade(prioridade)
        ];
    }

    
    public int getCorrigidosPorPrioridade(
            Prioridade prioridade) {

        return corrigidosPorPrioridade[
            indicePrioridade(prioridade)
        ];
    }

    
    public void exibirResumo() {

        System.out.println("MÉTRICAS DA SIMULAÇÃO");

        System.out.println("Bugs recebidos: " + totalRecebidos);
        System.out.println("Bugs recusados: " + totalRecusados);

        System.out.println(
            "Bugs aceitos: " + (totalRecebidos - totalRecusados)
        );

        System.out.println("Bugs classificados: " + totalClassificados);
        System.out.println("Bugs corrigidos: " + totalCorrigidos);

        System.out.println("POR PRIORIDADE");

        for (Prioridade prioridade : Prioridade.values()) {

            System.out.println("\n" + prioridade);

            System.out.println(
                "Classificados: "
                + getClassificadosPorPrioridade(prioridade)
            );

            System.out.println(
                "Corrigidos: "
                + getCorrigidosPorPrioridade(prioridade)
            );

            System.out.printf(
                "Espera média na fila: %.2f rodadas%n",
                getTempoMedioPrioridade(prioridade)
            );
        }

        System.out.println("TEMPOS DE ESPERA");

        System.out.printf(
            "Espera média na triagem: %.2f rodadas%n",
            getTempoMedioTriagem()
        );

        System.out.println("UTILIZAÇÃO DOS RECURSOS");

        System.out.printf(
            "Analistas QA: %.2f%%%n",
            getUtilizacaoAnalistas()
        );

        System.out.printf(
            "Desenvolvedores: %.2f%%%n",
            getUtilizacaoDesenvolvedores()
        );


    }
}
