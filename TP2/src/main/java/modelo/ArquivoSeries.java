package modelo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

import aeds3.Arquivo;
import aeds3.ParIntInt;
import aeds3.ArvoreBMais;
import entidades.Serie;
import entidades.Ator;

public class ArquivoSeries extends Arquivo<Serie> {

  public ArquivoSeries() throws Exception {
    super("series_db", (Constructor<Serie>) (Constructor<?>) Serie.class.getConstructor());
  }

  // 1. Vincular uma série a vários atores
  public void vincularAtores(int idSerie, ArvoreBMais<ParIntInt> serieAtores, ArvoreBMais<ParIntInt> atorSeries) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Digite os IDs dos atores para vincular à série (separados por espaço):");
    String linha = scanner.nextLine();
    String[] ids = linha.trim().split(" ");
    
    for (String s : ids) {
      try {
        int idAtor = Integer.parseInt(s);
        serieAtores.create(new ParIntInt(idSerie, idAtor));
        atorSeries.create(new ParIntInt(idAtor, idSerie));
      } catch (Exception e) {
        System.out.println("ID inválido ou vínculo já existente: " + s);
      }
    }
  }

  // 2. Exibir atores vinculados a uma série
  public void exibirAtoresDaSerie(int idSerie, ArvoreBMais<ParIntInt> serieAtores, ArquivoAtor arquivoAtor) throws Exception {
    ParIntInt chave = new ParIntInt(idSerie);
    ArrayList<ParIntInt> pares = serieAtores.read(chave);

    System.out.println("Atores dessa série:");
    if (pares != null) {
      for (ParIntInt par : pares) {
        Ator a = arquivoAtor.read(par.getNum2());
        System.out.println("→ " + a.getNome());
      }
    } else {
      System.out.println("Nenhum ator vinculado.");
    }
  }

  // 3. Remover todos os vínculos da série
  public void removerVinculos(int idSerie, ArvoreBMais<ParIntInt> serieAtores, ArvoreBMais<ParIntInt> atorSeries) throws Exception {
    ParIntInt chave = new ParIntInt(idSerie);
    ArrayList<ParIntInt> pares = serieAtores.read(chave);

    if (pares != null) {
      for (ParIntInt par : pares) {
        serieAtores.delete(par);
        atorSeries.delete(new ParIntInt(par.getNum2(), par.getNum1())); // Invertido
      }
    }
  }
}
