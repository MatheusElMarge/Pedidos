import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SistemaPedidos {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Map<String, Cliente> clienteMap = new HashMap<>();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("===== MENU =====");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Buscar/Alterar cliente");
            System.out.println("3. Realizar pedido");
            System.out.println("4. Encerrar pedido");
            System.out.println("5. Imprimir quantidade de pedidos");
            System.out.println("6. Imprimir quantidade de pedidos encerrados");
            System.out.println("7. Imprimir quantidade de pedidos em atendimento");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    buscarAlterarCliente();
                    break;
                case 3:
                    realizarPedido();
                    break;
                case 4:
                    encerrarPedido();
                    break;
                case 5:
                    imprimirQuantidadePedidos();
                    break;
                case 6:
                    imprimirQuantidadePedidosEncerrados();
                    break;
                case 7:
                    imprimirQuantidadePedidosEmAtendimento();
                    break;
                case 8:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 7);
    }

    private static void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(nome, endereco);
        clientes.add(cliente);
        clienteMap.put(nome, cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void buscarAlterarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        Cliente cliente = clienteMap.get(nome);

        if (cliente != null) {
            System.out.println("Dados do cliente encontrados: ");
            System.out.println(cliente);

            System.out.print("Deseja alterar o endereço? (S/N): ");
            String opcao = scanner.nextLine();
            if (opcao.equalsIgnoreCase("S")) {
                System.out.print("Digite o novo endereço: ");
                String novoEndereco = scanner.nextLine();
                cliente.setEndereco(novoEndereco);
                System.out.println("Endereço alterado com sucesso!");
            }
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    private static void realizarPedido() {
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        Cliente cliente = clienteMap.get(nomeCliente);

        if (cliente != null) {
            System.out.println("Cliente encontrado: ");
            System.out.println(cliente);

            System.out.print("Digite o endereço de entrega: ");
            String enderecoEntrega = scanner.nextLine();

            Pedido pedido = new Pedido(nomeCliente, enderecoEntrega);
            pedidos.add(pedido);

            System.out.println("Pedido realizado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    private static void imprimirQuantidadePedidos() {
        System.out.println("Quantidade de pedidos: " + pedidos.size());
    }

    private static void imprimirQuantidadePedidosEncerrados() {
        long pedidosEncerrados = pedidos.stream().filter(Pedido::isEncerrado).count();
        System.out.println("Quantidade de pedidos encerrados: " + pedidosEncerrados);
    }

    private static void imprimirQuantidadePedidosEmAtendimento() {
        long pedidosEmAtendimento = pedidos.stream().filter(pedido -> !pedido.isEncerrado()).count();
        System.out.println("Quantidade de pedidos em atendimento: " + pedidosEmAtendimento);
    }
    private static void encerrarPedido() {
        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos em andamento para encerrar.");
            return;
        }

        System.out.println("Pedidos em andamento:");
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            if (!pedido.isEncerrado()) {
                System.out.println((i + 1) + ". " + pedido.getCliente() + " - " + pedido.getEndereco());
            }
        }

        System.out.print("Digite o número do pedido a ser encerrado: ");
        int numeroPedido = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        int index = numeroPedido - 1;
        if (index >= 0 && index < pedidos.size()) {
            Pedido pedido = pedidos.get(index);
            if (!pedido.isEncerrado()) {
                pedido.encerrarPedido();
                System.out.println("Pedido encerrado com sucesso!");
            } else {
                System.out.println("Este pedido já está encerrado.");
            }
        } else {
            System.out.println("Número de pedido inválido.");
        }
    }
    // Método para gerar o arquivo TXT de entrega para pedidos encerrados
    private static void gerarArquivoTxtEntrega() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("entrega_pedidos.txt"));

            for (Pedido pedido : pedidos) {
                if (pedido.isEncerrado()) {
                    writer.println("Nome cliente: " + pedido.getCliente());
                    writer.println("Endereço: " + pedido.getEndereco());
                    writer.println();
                }
            }

            writer.close();
            System.out.println("Arquivo TXT de entrega gerado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo TXT de entrega: " + e.getMessage());
        }
    }

}