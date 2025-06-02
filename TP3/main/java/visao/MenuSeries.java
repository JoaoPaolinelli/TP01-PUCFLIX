package visao;

import java.util.Scanner;

import entidades.Serie;

public class MenuSeries {
  static Scanner sc = new Scanner(System.in);

  public static Serie lerSerie() {
    System.out.print("Nome da série: ");
    String nome = sc.nextLine();
    System.out.print("Ano de lançamento: ");
    short ano = Short.parseShort(sc.nextLine());
    System.out.print("Sinopse: ");
    String sinopse = sc.nextLine();
    System.out.print("Streaming (Netflix, Prime, etc): ");
    String streaming = sc.nextLine();

    return new Serie(nome, ano, sinopse, streaming);
  }

  public static void mostrarSerie(Serie serie) {
    System.out.println("\n-- Série --");
    System.out.println("ID: " + serie.getID());
    System.out.println("Nome: " + serie.getNome());
    System.out.println("Ano de Lançamento: " + serie.getAnoLancamento());
    System.out.println("Sinopse: " + serie.getSinopse());
    System.out.println("Streaming: " + serie.getStreaming());
  }
}