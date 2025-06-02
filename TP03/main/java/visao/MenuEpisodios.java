
package visao;

import entidades.Episodio;
import java.util.Scanner;

public class MenuEpisodios {
  static Scanner sc = new Scanner(System.in);

  public static Episodio lerEpisodio(int idSerie) {
    System.out.print("Nome do episódio: ");
    String nome = sc.nextLine();
    System.out.print("Temporada: ");
    short temporada = Short.parseShort(sc.nextLine());
    System.out.print("Data de lançamento: ");
    String data = sc.nextLine();
    System.out.print("Duração (min): ");
    int duracao = Integer.parseInt(sc.nextLine());
    System.out.print("Sinopse: ");
    String sinopse = sc.nextLine();

    return new Episodio(idSerie, nome, temporada, data, duracao, sinopse);
  }

  public static void mostrarEpisodio(Episodio ep) {
    System.out.println("\n-- Episódio --");
    System.out.println("ID: " + ep.getID());
    System.out.println("Série ID: " + ep.getIdSerie());
    System.out.println("Nome: " + ep.getNome());
    System.out.println("Temporada: " + ep.getTemporada());
    System.out.println("Data de Lançamento: " + ep.getDataLancamento());
    System.out.println("Duração: " + ep.getDuracao() + " min");
    System.out.println("Sinopse: " + ep.getSinopse());
  }
}
