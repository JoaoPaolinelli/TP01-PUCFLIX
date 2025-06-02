package controle;

import java.util.List;
import java.util.Scanner;

import aeds3.ArvoreBMais;
import aeds3.ParIntInt;
import entidades.Ator;
import modelo.ArquivoAtor;
import modelo.ArquivoSeries;

public class ControleAtores {

    private ArquivoAtor arqAtor;
    private ArquivoSeries arqSerie;
    private ArvoreBMais<ParIntInt> atorSeries;
    private ControleIndices controleIndices;

    public ControleAtores(ArquivoAtor arqAtor, ArquivoSeries arqSerie, ArvoreBMais<ParIntInt> atorSeries, ControleIndices ci) {
        this.arqAtor = arqAtor;
        this.arqSerie = arqSerie;
        this.atorSeries = atorSeries;
        this.controleIndices = ci;
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            System.out.println("\n-- Menu de Atores --");
            System.out.println("1) Cadastrar Ator");
            System.out.println("2) Listar Atores");
            System.out.println("3) Buscar por nome exato");
            System.out.println("4) Buscar por Termos (TF-IDF)");
            System.out.println("5) Excluir Ator");
            System.out.println("0) Voltar");
            System.out.print("> ");
            opc = Integer.parseInt(sc.nextLine());

            try {
                switch (opc) {
                    case 1 ->
                        cadastrar();
                    case 2 ->
                        listar();
                    case 3 ->
                        buscarPorNome();
                    case 4 ->
                        buscarPorTermos();
                    case 5 ->
                        excluir();
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opc != 0);
    }

    private void buscarPorNome() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o nome exato do ator: ");
        String nome = sc.nextLine();
        Ator a = arqAtor.buscarPorNome(nome);
        if (a != null) {
            System.out.println("\nAtor encontrado:");
            System.out.println(a);
            arqAtor.exibirSeriesDoAtor(a.getID(), atorSeries, arqSerie);
        } else {
            System.out.println("Ator não encontrado com este nome.");
        }
    }

    private void buscarPorTermos() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nDigite os termos para busca de ator: ");
        String consulta = sc.nextLine();
        List<ResultadoBusca> resultados = controleIndices.buscarAtor(consulta);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum ator encontrado com os termos informados.");
            return;
        }

        System.out.println("\nResultados da busca (ordenado por relevância):");
        for (ResultadoBusca res : resultados) {
            Ator ator = arqAtor.read(res.getId());
            if (ator != null) {
                System.out.println(res);
                System.out.println(ator);
            }
        }
    }

    private void cadastrar() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nome do ator: ");
        String nome = sc.nextLine();
        Ator ator = new Ator(nome);

        int id = arqAtor.create(ator);

        controleIndices.indexarAtor(ator);

        System.out.println("Ator cadastrado com ID: " + id);
    }

    private void listar() throws Exception {
        Ator[] todos = arqAtor.readAll();
        if (todos.length == 0) {
            System.out.println("Nenhum ator cadastrado.");
            return;
        }
        for (Ator a : todos) {
            System.out.println(a);
            arqAtor.exibirSeriesDoAtor(a.getID(), atorSeries, arqSerie);
        }
    }

    private void excluir() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID do ator a excluir: ");
        int id = Integer.parseInt(sc.nextLine());
        Ator ator = arqAtor.read(id);

        if (ator == null) {
            System.out.println("Ator não encontrado.");
            return;
        }

        if (arqAtor.podeExcluir(id, atorSeries)) {

            controleIndices.desindexarAtor(ator);

            arqAtor.delete(id);
            System.out.println("Ator excluído com sucesso.");
        } else {
            System.out.println("Não é possível excluir: o ator está vinculado a uma ou mais séries.");
        }
    }
}
