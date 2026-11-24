
public class App {

    public static void main(String[] args) {

        
        Configuracao configuracao = new Configuracao();

        
        Simulacao simulacao = new Simulacao(configuracao);

        simulacao.executar();
    }
}
