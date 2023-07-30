public class Pedido {
    private String cliente;
    private String endereco;
    private boolean encerrado;

    public Pedido(String cliente, String endereco) {
        this.cliente = cliente;
        this.endereco = endereco;
        this.encerrado = false;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrarPedido() {
        this.encerrado = true;
    }
}