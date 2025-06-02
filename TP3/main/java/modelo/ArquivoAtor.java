package modelo;

import entidades.Ator;
import entidades.Serie;
import aeds3.Arquivo;
import aeds3.ParIntInt;
import aeds3.ArvoreBMais;
import java.io.IOException;
import java.util.ArrayList;

public class ArquivoAtor extends Arquivo<Ator> {

  public ArquivoAtor() throws Exception {
    super("atores.db", Ator.class.getConstructor());
  }

  public Ator buscarPorNome(String nomeProcurado) throws Exception {
    Ator[] todos = this.readAll();
    for (Ator a : todos) {
      if (a.getNome().equalsIgnoreCase(nomeProcurado)) {
        return a;
      }
    }
    return null;
  }

  public boolean podeExcluir(int idAtor, ArvoreBMais<ParIntInt> atorSerie) throws Exception {
    ParIntInt chave = new ParIntInt(idAtor);
    ArrayList<ParIntInt> pares = atorSerie.read(chave);
    return pares == null || pares.isEmpty();
  }

  public void exibirSeriesDoAtor(int idAtor, ArvoreBMais<ParIntInt> atorSerie, ArquivoSeries arquivoSerie) throws Exception {
    ParIntInt chave = new ParIntInt(idAtor);
    ArrayList<ParIntInt> pares = atorSerie.read(chave);
    if (pares != null) {
      for (ParIntInt par : pares) {
        Serie s = arquivoSerie.read(par.getNum2());
        System.out.println("→ " + s.getNome());
      }
    } else {
      System.out.println("Nenhuma série encontrada.");
    }
  }
}
