package controle;

import java.util.Scanner;
import java.util.ArrayList;

import modelo.ArquivoAtor;
import modelo.ArquivoSeries;
import entidades.Ator;
import entidades.Serie;
import aeds3.ArvoreBMais;
import aeds3.ParIntInt;

public class ControleAtores {

  private ArquivoAtor arqAtor;
  private ArquivoSeries arqSerie;
  private ArvoreBMais<ParIntInt> atorSeries;

  public ControleAtores(ArquivoAtor arqAtor, ArquivoSeries arqSerie, ArvoreBMais<ParIntInt> atorSeries) {
    this.arqAtor = arqAtor;
    this.arqSerie = arqSerie;
    this.atorSeries = atorSeries;
  }

  public void menu() {
    Scanner sc = new Scanner(System.in);
    int opc;
    do {
      System.out.println("\nMenu de Atores");
      System.out.println("1) Cadastrar Ator");
      System.out.println("2) Listar Atores");
      System.out.println("3) Buscar por nome");
      System.out.println("4) Excluir Ator");
      System.out.println("0) Voltar");
      System.out.print("> ");
      opc = Integer.parseInt(sc.nextLine());

      switch (opc) {
        case 1 -> cadastrar();
        case 2 -> listar();
        case 3 -> buscarPorNome();
        case 4 -> excluir();
      }
    } while (opc != 0);
  }

  private void cadastrar() {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.print("Nome do ator: ");
      String nome = sc.nextLine();
      int id = arqAtor.create(new Ator(nome));
      System.out.println("Ator cadastrado com ID: " + id);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listar() {
    try {
      Ator[] todos = arqAtor.readAll();
      for (Ator a : todos) {
        System.out.println(a);
        arqAtor.exibirSeriesDoAtor(a.getID(), atorSeries, arqSerie);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void buscarPorNome() {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.print("Digite o nome: ");
      String nome = sc.nextLine();
      Ator a = arqAtor.buscarPorNome(nome);
      if (a != null) {
        System.out.println(a);
        arqAtor.exibirSeriesDoAtor(a.getID(), atorSeries, arqSerie);
      } else {
        System.out.println("Ator não encontrado.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void excluir() {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.print("ID do ator a excluir: ");
      int id = Integer.parseInt(sc.nextLine());
      if (arqAtor.podeExcluir(id, atorSeries)) {
        arqAtor.delete(id);
        System.out.println("Ator excluído com sucesso.");
      } else {
        System.out.println("Não é possível excluir: ator está vinculado a séries.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
