package controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import aeds3.ArvoreBMais;
import aeds3.ParIntInt;
import entidades.Episodio;
import entidades.Serie;
import modelo.ArquivoAtor;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;
import visao.MenuEpisodios;
import visao.MenuSeries;

public class ControleSeries {

    private ArquivoSeries arqSeries;
    private ArquivoEpisodios arqEpisodios;
    private ArquivoAtor arqAtor;
    private ArvoreBMais<ParIntInt> serieAtores;
    private ArvoreBMais<ParIntInt> atorSeries;
    private ControleIndices controleIndices;
    private Scanner sc;

    public ControleSeries(ArquivoSeries arqSeries, ArquivoEpisodios arqEpisodios, ArquivoAtor arqAtor,
            ArvoreBMais<ParIntInt> serieAtores, ArvoreBMais<ParIntInt> atorSeries, ControleIndices ci) {
        this.arqSeries = arqSeries;
        this.arqEpisodios = arqEpisodios;
        this.arqAtor = arqAtor;
        this.serieAtores = serieAtores;
        this.atorSeries = atorSeries;
        this.controleIndices = ci;
        this.sc = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int opc;
        do {
            System.out.println("\nPUCFlix 1.0 - Séries");
            System.out.println("1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Buscar por Termos (TF-IDF)");
            System.out.println("4) Alterar");
            System.out.println("5) Excluir");
            System.out.println("6) Visualizar episódios por temporada");
            System.out.println("7) Listar todos os episódios da série");
            System.out.println("0) Retornar ao menu anterior");
            System.out.print("> ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1 ->
                    incluir();
                case 2 ->
                    buscar();
                case 3 ->
                    buscarPorTermos();
                case 4 ->
                    alterar();
                case 5 ->
                    excluir();
                case 6 ->
                    visualizarPorTemporada();
                case 7 ->
                    listarTodosEpisodios();
            }
        } while (opc != 0);
    }

    private void incluir() throws Exception {
        Serie s = MenuSeries.lerSerie();
        int id = arqSeries.create(s);
        System.out.println("Série cadastrada com ID: " + id);
        arqSeries.vincularAtores(id, serieAtores, atorSeries);
    }

    private void buscar() throws Exception {
        Serie s = MenuSeries.lerSerie();
        s.setID(arqSeries.getUltimoID() + 1);
        arqSeries.create(s);
        System.out.println("Série cadastrada com ID: " + s.getID());
        controleIndices.indexarSerie(s);
        arqSeries.vincularAtores(s.getID(), serieAtores, atorSeries);
    }

    private void buscarPorTermos() throws Exception {
        System.out.print("\nDigite os termos para busca: ");
        String consulta = sc.nextLine();
        List<ResultadoBusca> resultados = controleIndices.buscarSerie(consulta);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com os termos informados.");
            return;
        }

        System.out.println("\nResultados da busca (ordenado por relevância):");
        for (ResultadoBusca res : resultados) {
            Serie s = arqSeries.read(res.getId());
            if (s != null) {
                System.out.println(res);
                MenuSeries.mostrarSerie(s);
            }
        }
    }

    private void alterar() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie sAntiga = arqSeries.read(id);
        if (sAntiga != null) {
            controleIndices.desindexarSerie(sAntiga);

            Serie nova = MenuSeries.lerSerie();
            nova.setID(id);
            arqSeries.update(nova);

            controleIndices.indexarSerie(nova);
            System.out.println("Série atualizada.");
            arqSeries.vincularAtores(id, serieAtores, atorSeries);
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void excluir() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());

        Serie s = arqSeries.read(id); // NOVO
        if (s == null) {
            System.out.println("Série não encontrada.");
            return;
        }

        int[] ids = arqEpisodios.buscarEpisodiosPorSerie(id);
        if (ids.length > 0) {
            System.out.println("Não é possível excluir. Existem episódios vinculados.");
            return;
        }

        controleIndices.desindexarSerie(s); // NOVO
        arqSeries.removerVinculos(id, serieAtores, atorSeries);
        boolean ok = arqSeries.delete(id);
        if (ok) {
            System.out.println("Série excluída.");
        } else {
            System.out.println("Erro ao excluir série.");
        }
    }

    private void visualizarPorTemporada() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie s = arqSeries.read(id);
        if (s == null) {
            System.out.println("Série não encontrada.");
            return;
        }

        int[] ids = arqEpisodios.buscarEpisodiosPorSerie(id);
        if (ids.length == 0) {
            System.out.println("Nenhum episódio encontrado para esta série.");
            return;
        }

        Map<Short, List<Episodio>> temporadas = new TreeMap<>();

        for (int eid : ids) {
            Episodio e = arqEpisodios.read(eid);
            if (e != null) {
                temporadas.putIfAbsent(e.getTemporada(), new ArrayList<>());
                temporadas.get(e.getTemporada()).add(e);
            }
        }

        System.out.println("\nEpisódios da série: " + s.getNome());
        for (short temp : temporadas.keySet()) {
            System.out.println("\n-- Temporada " + temp + " --");
            for (Episodio e : temporadas.get(temp)) {
                MenuEpisodios.mostrarEpisodio(e);
            }
        }
    }

    private void listarTodosEpisodios() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());

        Serie s = arqSeries.read(id);
        if (s == null) {
            System.out.println("Série não encontrada.");
            return;
        }

        int[] ids = arqEpisodios.buscarEpisodiosPorSerie(id);
        if (ids.length == 0) {
            System.out.println("Nenhum episódio encontrado para esta série.");
            return;
        }

        System.out.println("\nTodos os episódios da série: " + s.getNome());
        for (int eid : ids) {
            Episodio e = arqEpisodios.read(eid);
            if (e != null) {
                MenuEpisodios.mostrarEpisodio(e);
            }
        }
    }
}
