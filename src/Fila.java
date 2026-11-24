
public class Fila {

    private Bug[] elementos;
    private int inicio;
    private int fim;
    private int quantidade;
    private int capacidade;

    
    public Fila(int capacidade) {

        this.capacidade = capacidade;
        this.elementos = new Bug[capacidade];

        this.inicio = 0;
        this.fim = 0;
        this.quantidade = 0;
    }

    
    public boolean enfileirar(Bug bug) {

        if (estaCheia()) {
            return false;
        }

        elementos[fim] = bug;

        fim = (fim + 1) % capacidade;

        quantidade++;

        return true;
    }

    
    public Bug desenfileirar() {

        if (estaVazia()) {
            return null;
        }

        Bug bug = elementos[inicio];

        elementos[inicio] = null;

        inicio = (inicio + 1) % capacidade;

        quantidade--;

        return bug;
    }

    
    public Bug consultarInicio() {

        if (estaVazia()) {
            return null;
        }

        return elementos[inicio];
    }

    
    public boolean estaVazia() {

        return quantidade == 0;
    }

    
    public boolean estaCheia() {

        return quantidade == capacidade;
    }

    
    public int tamanho() {

        return quantidade;
    }

    
    public int getCapacidade() {

        return capacidade;
    }

    
    public void mostrarFila() {

        if (estaVazia()) {
            System.out.println("Fila vazia!");
            return;
        }

        System.out.println("Elementos da fila:");

        for (int i = 0; i < quantidade; i++) {

            int posicao = (inicio + i) % capacidade;

            System.out.println(elementos[posicao]);
        }
    }
}
