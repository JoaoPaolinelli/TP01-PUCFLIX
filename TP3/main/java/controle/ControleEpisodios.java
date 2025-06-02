package controle;

import java.util.List;
import java.util.Scanner;

import entidades.Episodio;
import modelo.ArquivoEpisodios;
import visao.MenuEpisodios;

public class ControleEpisodios {

    private ArquivoEpisodios arqEpisodios;
    private int idSerie;
    private ControleIndices controleIndices;
    private Scanner sc;

    public ControleEpisodios(ArquivoEpisodios arqEpisodios, int idSerie, ControleIndices ci) {
        this.arqEpisodios = arqEpisodios;
        this.idSerie = idSerie;
        this.controleIndices = ci; // NOVO
        this.sc = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int opc;
        do {
            System.out.println("\n-- Menu Episódios (Série ID: " + idSerie + ") --");
            System.out.println("1) Incluir Episódio");
            System.out.println("2) Buscar Episódio por ID");
            System.out.println("3) Alterar Episódio");
            System.out.println("4) Excluir Episódio");
            System.out.println("5) Buscar Episódio por Termos (em TODAS as séries)");
            System.out.println("0) Retornar ao menu anterior");
            System.out.print("> ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1 ->
                    incluir();
                case 2 ->
                    buscar();
                case 3 ->
                    alterar();
                case 4 ->
                    excluir();
                case 5 ->
                    buscarPorTermos();
            }
        } while (opc != 0);
    }

    private void incluir() throws Exception {
        Episodio e = MenuEpisodios.lerEpisodio(idSerie);

        int id = arqEpisodios.create(e);

        controleIndices.indexarEpisodio(e);

        System.out.println("Episódio cadastrado com ID: " + id);
    }

    private void buscar() throws Exception {
        System.out.print("ID do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio e = arqEpisodios.read(id);
        if (e != null && e.getIdSerie() == idSerie) {
            MenuEpisodios.mostrarEpisodio(e);
        } else {
            System.out.println("Episódio não encontrado para esta série.");
        }
    }

    private void alterar() throws Exception {
        System.out.print("ID do episódio para alterar: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio eAntigo = arqEpisodios.read(id);

        if (eAntigo != null && eAntigo.getIdSerie() == idSerie) {
            controleIndices.desindexarEpisodio(eAntigo);

            Episodio novo = MenuEpisodios.lerEpisodio(idSerie);
            novo.setID(id);
            arqEpisodios.update(novo);

            controleIndices.indexarEpisodio(novo);
            System.out.println("Episódio atualizado com sucesso.");
        } else {
            System.out.println("Episódio não encontrado para esta série.");
        }
    }

    private void excluir() throws Exception {
        System.out.print("ID do episódio para excluir: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio e = arqEpisodios.read(id);

        if (e != null && e.getIdSerie() == idSerie) {
            controleIndices.desindexarEpisodio(e);

            boolean ok = arqEpisodios.delete(id);
            if (ok) {
                System.out.println("Episódio excluído com sucesso.");
            } else {
                System.out.println("Erro ao excluir episódio.");
            }
        } else {
            System.out.println("Episódio não encontrado para esta série.");
        }
    }

    private void buscarPorTermos() throws Exception {
        System.out.print("\nDigite os termos para busca de episódio (global): ");
        String consulta = sc.nextLine();
        List<ResultadoBusca> resultados = controleIndices.buscarEpisodio(consulta);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum episódio encontrado com os termos informados.");
            return;
        }

        System.out.println("\nResultados da busca (ordenado por relevância):");
        for (ResultadoBusca res : resultados) {
            Episodio ep = arqEpisodios.read(res.getId());
            if (ep != null) {
                System.out.println("\n" + res); // Mostra ID e Score
                MenuEpisodios.mostrarEpisodio(ep);
            }
        }
    }

}
