package controle;

import modelo.*;
import entidades.Episodio;
import visao.MenuEpisodios;
import java.util.Scanner;

public class ControleEpisodios {
  private ArquivoEpisodios arqEpisodios;
  private int idSerie;
  private Scanner sc;

  public ControleEpisodios(ArquivoEpisodios arqEpisodios, int idSerie) {
    this.arqEpisodios = arqEpisodios;
    this.idSerie = idSerie;
    this.sc = new Scanner(System.in);
  }

  public void menu() throws Exception {
    int opc;
    do {
      System.out.println("\nPUCFlix 1.0 - Episódios da Série " + idSerie);
      System.out.println("1) Incluir");
      System.out.println("2) Buscar");
      System.out.println("3) Alterar");
      System.out.println("4) Excluir");
      System.out.println("0) Retornar ao menu anterior");
      System.out.print("> ");
      opc = Integer.parseInt(sc.nextLine());

      switch (opc) {
        case 1 -> incluir();
        case 2 -> buscar();
        case 3 -> alterar();
        case 4 -> excluir();
      }
    } while (opc != 0);
  }

  private void incluir() throws Exception {
    Episodio e = MenuEpisodios.lerEpisodio(idSerie);
    int id = arqEpisodios.create(e);
    System.out.println("Episódio cadastrado com ID: " + id);
  }

  private void buscar() throws Exception {
    System.out.print("ID do episódio: ");
    int id = Integer.parseInt(sc.nextLine());
    Episodio e = arqEpisodios.read(id);
    if (e != null && e.getIdSerie() == idSerie) MenuEpisodios.mostrarEpisodio(e);
    else System.out.println("Episódio não encontrado para esta série.");
  }

  private void alterar() throws Exception {
    System.out.print("ID do episódio: ");
    int id = Integer.parseInt(sc.nextLine());
    Episodio e = arqEpisodios.read(id);
    if (e != null && e.getIdSerie() == idSerie) {
      Episodio novo = MenuEpisodios.lerEpisodio(idSerie);
      novo.setID(id);
      arqEpisodios.update(novo);
      System.out.println("Episódio atualizado.");
    } else {
      System.out.println("Episódio não encontrado para esta série.");
    }
  }

  private void excluir() throws Exception {
    System.out.print("ID do episódio: ");
    int id = Integer.parseInt(sc.nextLine());
    Episodio e = arqEpisodios.read(id);
    if (e != null && e.getIdSerie() == idSerie) {
      boolean ok = arqEpisodios.delete(id);
      if (ok) System.out.println("Episódio excluído.");
      else System.out.println("Erro ao excluir episódio.");
    } else {
      System.out.println("Episódio não encontrado para esta série.");
    }
  }
}