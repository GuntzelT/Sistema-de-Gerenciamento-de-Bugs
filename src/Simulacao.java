
import java.util.Random;

public class Simulacao {

    private Configuracao configuracao;
    private Metricas metricas;
    private Random aleatorio;

    private Fila filaTriagem;
    private Fila[] filasPrioridade;

    private AnalistaQA[] analistas;
    private Desenvolvedor[] desenvolvedores;

    private int proximoIdBug;


    public Simulacao(Configuracao configuracao) {

        if (configuracao == null) {
            throw new IllegalArgumentException(
                "A configuração não pode ser nula."
            );
        }

        this.configuracao = configuracao;

        
        this.aleatorio = new Random(
            configuracao.getSementeAleatoria()
        );

        this.proximoIdBug = 1;

        
        this.filaTriagem = new Fila(
            configuracao.getCapacidadeFilaEntrada()
        );

        this.filasPrioridade = new Fila[4];

        for (int i = 0; i < filasPrioridade.length; i++) {
            filasPrioridade[i] = new Fila(
                configuracao.getTotalRodadas()
            );
        }

        
        this.analistas = new AnalistaQA[
            configuracao.getQuantidadeAnalistas()
        ];

        for (int i = 0; i < analistas.length; i++) {
            analistas[i] = new AnalistaQA(i + 1);
        }

        
        this.desenvolvedores = new Desenvolvedor[
            configuracao.getQuantidadeDesenvolvedores()
        ];

        for (int i = 0; i < desenvolvedores.length; i++) {
            desenvolvedores[i] = new Desenvolvedor(i + 1);
        }

        
        this.metricas = new Metricas(
            configuracao.getTotalRodadas(),
            configuracao.getQuantidadeAnalistas(),
            configuracao.getQuantidadeDesenvolvedores()
        );
    }

    
    public void executar() {

        System.out.println("INICIANDO SIMULAÇÃO");
        configuracao.exibirConfiguracao();

        for (int rodada = 1;
             rodada <= configuracao.getTotalRodadas();
             rodada++) {
                
            rodadaAtual = rodada;

            System.out.println(
                "RODADA " + rodada);

            //Verifica a chegada de um novo bug
            gerarChegadaBug(rodada);

            //Avança as classificações que já estavam em andamento
            processarAnalistas(rodada);

            //Avança as correções que já estavam em andamento
            processarDesenvolvedores();

            //Distribui bugs aos analistas disponíveis
            distribuirBugsParaAnalistas();

            //Distribui bugs aos desenvolvedores disponíveis
            distribuirBugsParaDesenvolvedores();

            //Registra as métricas da rodada
            registrarMetricas(rodada);

            exibirEstadoRodada();
        }

        finalizarSimulacao();
    }

    
    private void gerarChegadaBug(int rodada) {

        double sorteio = aleatorio.nextDouble();

        if (sorteio < configuracao.getProbabilidadeChegada()) {

            Bug bug = new Bug(
                String.format("BUG-%03d", proximoIdBug),
                rodada
            );

            proximoIdBug++;

            metricas.registrarBugRecebido();

            if (filaTriagem.enfileirar(bug)) {

                System.out.println(
                    "Novo bug recebido: " + bug.getId()
                );

            } else {

                metricas.registrarBugRecusado();

                System.out.println(
                    "Bug recusado: fila de triagem cheia."
                );
            }
        }
    }


    private void processarAnalistas(int rodada) {

        for (AnalistaQA analista : analistas) {

            if (!analista.estaDisponivel()) {

                analista.processarRodada();

                if (analista.classificacaoConcluida()) {

                    Bug bug = analista.finalizarClassificacao();

                    Prioridade prioridade = sortearPrioridade();

                    bug.setPrioridade(prioridade);
                    bug.setRodadaClassificacao(rodada);

                    int indice = indicePrioridade(prioridade);

                    filasPrioridade[indice].enfileirar(bug);

                    metricas.registrarBugClassificado(bug);

                    System.out.println(
                        "Classificação concluída: " + bug.getId()
                        + " | Prioridade: " + prioridade
                    );
                }
            }
        }
    }

    private void processarDesenvolvedores() {

        for (Desenvolvedor desenvolvedor : desenvolvedores) {

            if (!desenvolvedor.estaDisponivel()) {

                desenvolvedor.processarRodada();

                if (desenvolvedor.correcaoConcluida()) {

                    Bug bug = desenvolvedor.finalizarCorrecao();

                    metricas.registrarBugCorrigido(bug);

                    System.out.println(
                        "Bug corrigido: " + bug.getId()
                    );
                }
            }
        }
    }

    private void distribuirBugsParaAnalistas() {

        for (AnalistaQA analista : analistas) {

            if (analista.estaDisponivel()
                    && !filaTriagem.estaVazia()) {

                Bug bug = filaTriagem.desenfileirar();

                analista.iniciarClassificacao(
                    bug,
                    configuracao.getTempoClassificacao()
                );

                System.out.println(
                    "Analista " + analista.getId()
                    + " iniciou a classificação de " + bug.getId()
                );
            }
        }
    }

    private void distribuirBugsParaDesenvolvedores() {

        for (Desenvolvedor desenvolvedor : desenvolvedores) {

            if (desenvolvedor.estaDisponivel()) {

                Bug bug = retirarBugMaiorPrioridade();

                if (bug != null) {

                    desenvolvedor.iniciarCorrecao(
                        bug,
                        configuracao.getTempoCorrecao(),
                        obterRodadaAtual()
                    );

                    System.out.println(
                        "Desenvolvedor " + desenvolvedor.getId()
                        + " iniciou a correção de " + bug.getId()
                        + " | Prioridade: " + bug.getPrioridade()
                    );
                }
            }
        }
    }

    
    private Bug retirarBugMaiorPrioridade() {

        for (int i = 0; i < filasPrioridade.length; i++) {

            if (!filasPrioridade[i].estaVazia()) {
                return filasPrioridade[i].desenfileirar();
            }
        }

        return null;
    }

    
    private Prioridade sortearPrioridade() {

        double sorteio = aleatorio.nextDouble();

        double critica = configuracao.getProbabilidadeCritica();
        double alta = configuracao.getProbabilidadeAlta();
        double media = configuracao.getProbabilidadeMedia();

        if (sorteio < critica) {
            return Prioridade.CRITICA;
        }

        if (sorteio < critica + alta) {
            return Prioridade.ALTA;
        }

        if (sorteio < critica + alta + media) {
            return Prioridade.MEDIA;
        }

        return Prioridade.BAIXA;
    }

    
    private int indicePrioridade(Prioridade prioridade) {

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

    
    private void registrarMetricas(int rodada) {

        int bugsAguardando = filaTriagem.tamanho();

        for (Fila fila : filasPrioridade) {
            bugsAguardando += fila.tamanho();
        }

        metricas.registrarBugsAguardando(
            rodada,
            bugsAguardando
        );

        int analistasOcupados = 0;

        for (AnalistaQA analista : analistas) {
            if (!analista.estaDisponivel()) {
                analistasOcupados++;
            }
        }

        int desenvolvedoresOcupados = 0;

        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            if (!desenvolvedor.estaDisponivel()) {
                desenvolvedoresOcupados++;
            }
        }

        metricas.registrarUtilizacao(
            analistasOcupados,
            desenvolvedoresOcupados
        );
    }

    
    private void exibirEstadoRodada() {

        System.out.println("FILA DE TRIAGEM");
        System.out.println(
            "Bugs aguardando: " + filaTriagem.tamanho()
        );

        System.out.println("FILAS DE PRIORIDADE");

        for (int i = 0; i < filasPrioridade.length; i++) {

            System.out.println(
                Prioridade.values()[i] + ": "
                + filasPrioridade[i].tamanho()
                + " bugs aguardando"
            );
        }

        System.out.println("ANALISTAS QA");

        for (AnalistaQA analista : analistas) {
            analista.exibirEstado();
        }

        System.out.println("DESENVOLVEDORES");

        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            desenvolvedor.exibirEstado();
        }
    }

    
    private void finalizarSimulacao() {

        System.out.println("FIM DA SIMULAÇÃO");

        metricas.exibirResumo();

        System.out.println("BUGS AINDA AGUARDANDO NA TRIAGEM");
        System.out.println(
            filaTriagem.tamanho() + " bugs aguardando."
        );

        System.out.println("BUGS AGUARDANDO POR PRIORIDADE");

        for (int i = 0; i < filasPrioridade.length; i++) {

            System.out.println(
                Prioridade.values()[i] + ": "
                + filasPrioridade[i].tamanho()
                + " bugs aguardando."
            );
        }

        System.out.println("BUGS EM CLASSIFICAÇÃO");

        for (AnalistaQA analista : analistas) {

            if (!analista.estaDisponivel()) {
                System.out.println(
                    "Analista " + analista.getId()
                    + " está classificando "
                    + analista.getBugAtual().getId()
                );
            }
        }

        System.out.println("BUGS EM CORREÇÃO");

        for (Desenvolvedor desenvolvedor : desenvolvedores) {

            if (!desenvolvedor.estaDisponivel()) {
                System.out.println(
                    "Desenvolvedor " + desenvolvedor.getId()
                    + " está corrigindo "
                    + desenvolvedor.getBugAtual().getId()
                );
            }
        }

    }

    
    private int obterRodadaAtual() {
        return rodadaAtual;
    }

    private int rodadaAtual;
}
