package controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import entidades.Episodio;
import entidades.Serie;
import entidades.Ator;
import modelo.ArquivoAtor;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;
import visao.MenuEpisodios;
import visao.MenuSeries;
import aeds3.ParIntInt;
import aeds3.ArvoreBMais;

public class ControleSeries {
  private ArquivoSeries arqSeries;
  private ArquivoEpisodios arqEpisodios;
  private ArquivoAtor arqAtor;
  private ArvoreBMais<ParIntInt> serieAtores;
  private ArvoreBMais<ParIntInt> atorSeries;
  private Scanner sc;

  public ControleSeries(ArquivoSeries arqSeries, ArquivoEpisodios arqEpisodios, ArquivoAtor arqAtor,
                        ArvoreBMais<ParIntInt> serieAtores, ArvoreBMais<ParIntInt> atorSeries) {
    this.arqSeries = arqSeries;
    this.arqEpisodios = arqEpisodios;
    this.arqAtor = arqAtor;
    this.serieAtores = serieAtores;
    this.atorSeries = atorSeries;
    this.sc = new Scanner(System.in);
  }

  public void menu() throws Exception {
    int opc;
    do {
      System.out.println("\nPUCFlix 1.0 - Séries");
      System.out.println("1) Incluir");
      System.out.println("2) Buscar");
      System.out.println("3) Alterar");
      System.out.println("4) Excluir");
      System.out.println("5) Visualizar episódios por temporada");
      System.out.println("6) Listar todos os episódios da série");
      System.out.println("0) Retornar ao menu anterior");
      System.out.print("> ");
      opc = Integer.parseInt(sc.nextLine());

      switch (opc) {
        case 1 -> incluir();
        case 2 -> buscar();
        case 3 -> alterar();
        case 4 -> excluir();
        case 5 -> visualizarPorTemporada();
        case 6 -> listarTodosEpisodios();
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
    System.out.print("ID da série: ");
    int id = Integer.parseInt(sc.nextLine());
    Serie s = arqSeries.read(id);
    if (s != null) {
      MenuSeries.mostrarSerie(s);
      arqSeries.exibirAtoresDaSerie(id, serieAtores, arqAtor);
    } else {
      System.out.println("Série não encontrada.");
    }
  }

  private void alterar() throws Exception {
    System.out.print("ID da série: ");
    int id = Integer.parseInt(sc.nextLine());
    Serie s = arqSeries.read(id);
    if (s != null) {
      Serie nova = MenuSeries.lerSerie();
      nova.setID(id);
      arqSeries.update(nova);
      System.out.println("Série atualizada.");
      arqSeries.vincularAtores(id, serieAtores, atorSeries);
    } else {
      System.out.println("Série não encontrada.");
    }
  }

  private void excluir() throws Exception {
    System.out.print("ID da série: ");
    int id = Integer.parseInt(sc.nextLine());

    int[] ids = arqEpisodios.buscarEpisodiosPorSerie(id);
    if (ids.length > 0) {
      System.out.println("Não é possível excluir. Existem episódios vinculados.");
      return;
    }

    arqSeries.removerVinculos(id, serieAtores, atorSeries);
    boolean ok = arqSeries.delete(id);
    if (ok) System.out.println("Série excluída.");
    else System.out.println("Série não encontrada.");
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
