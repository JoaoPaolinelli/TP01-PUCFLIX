import java.lang.reflect.Constructor;
import java.util.Scanner;

import controle.ControleAtores;
import controle.ControleEpisodios;
import controle.ControleSeries;
import entidades.Ator;
import entidades.Episodio;
import entidades.Serie;
import modelo.ArquivoAtor;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;
import aeds3.ParIntInt;
import aeds3.ArvoreBMais;

public class PrincipalFlix {

  public static void main(String[] args) {
    try {
      Scanner sc = new Scanner(System.in);

      // Inicializa arquivos
      ArquivoSeries arqSeries = new ArquivoSeries();
      ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
      ArquivoAtor arqAtor = new ArquivoAtor();

      // Inicializa árvores de relacionamento N:N
    Constructor<ParIntInt> construtor = ParIntInt.class.getConstructor();

    ArvoreBMais<ParIntInt> serieAtores = new ArvoreBMais<>(construtor, 4, "serie-atores.db");
    ArvoreBMais<ParIntInt> atorSeries = new ArvoreBMais<>(construtor, 4, "ator-series.db");

      // Inicializa controles
      ControleSeries controleSeries = new ControleSeries(arqSeries, arqEpisodios, arqAtor, serieAtores, atorSeries);
      ControleAtores controleAtores = new ControleAtores(arqAtor, arqSeries, atorSeries);

      int opc;
      do {
        System.out.println("\nPUCFlix 2.0 - Menu Principal");
        System.out.println("1) Séries");
        System.out.println("2) Episódios (por série)");
        System.out.println("3) Atores");
        System.out.println("9) Popular base de dados (modo dev)");
        System.out.println("0) Sair");
        System.out.print("> ");
        opc = Integer.parseInt(sc.nextLine());

        switch (opc) {
          case 1 -> controleSeries.menu();
          case 2 -> {
            System.out.print("Digite o ID da série: ");
            int idSerie = Integer.parseInt(sc.nextLine());
            ControleEpisodios controleEpisodios = new ControleEpisodios(arqEpisodios, idSerie);
            controleEpisodios.menu();
          }
          case 3 -> controleAtores.menu();
          case 9 -> popularDados();
        }
      } while (opc != 0);

      System.out.println("Programa finalizado.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void popularDados() {
  try {
    ArquivoSeries arqSeries = new ArquivoSeries();
    ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
    ArquivoAtor arqAtor = new ArquivoAtor();

    Constructor<ParIntInt> construtor = ParIntInt.class.getConstructor();
    ArvoreBMais<ParIntInt> serieAtores = new ArvoreBMais<>(construtor, 4, "serie-atores.db");
    ArvoreBMais<ParIntInt> atorSeries = new ArvoreBMais<>(construtor, 4, "ator-series.db");

    // Atores
    int idA1 = arqAtor.create(new Ator("Bryan Cranston"));
    int idA2 = arqAtor.create(new Ator("Aaron Paul"));
    int idA3 = arqAtor.create(new Ator("Millie Bobby Brown"));
    int idA4 = arqAtor.create(new Ator("Finn Wolfhard"));
    int idA5 = arqAtor.create(new Ator("Karl Urban"));
    int idA6 = arqAtor.create(new Ator("Jack Quaid"));

    // Séries
    Serie s1 = new Serie("Breaking Bad", (short) 2008, "Um professor de química vira traficante.", "Netflix");
    Serie s2 = new Serie("Stranger Things", (short) 2016, "Crianças enfrentam o Mundo Invertido.", "Netflix");
    Serie s3 = new Serie("The Boys", (short) 2019, "Super-heróis corruptos enfrentam vigilantes.", "Prime Video");

    int idS1 = arqSeries.create(s1);
    int idS2 = arqSeries.create(s2);
    int idS3 = arqSeries.create(s3);

    // Vincular atores às séries
    serieAtores.create(new ParIntInt(idS1, idA1));
    serieAtores.create(new ParIntInt(idS1, idA2));
    atorSeries.create(new ParIntInt(idA1, idS1));
    atorSeries.create(new ParIntInt(idA2, idS1));

    serieAtores.create(new ParIntInt(idS2, idA3));
    serieAtores.create(new ParIntInt(idS2, idA4));
    atorSeries.create(new ParIntInt(idA3, idS2));
    atorSeries.create(new ParIntInt(idA4, idS2));

    serieAtores.create(new ParIntInt(idS3, idA5));
    serieAtores.create(new ParIntInt(idS3, idA6));
    atorSeries.create(new ParIntInt(idA5, idS3));
    atorSeries.create(new ParIntInt(idA6, idS3));

    // Episódios
    arqEpisodios.create(new Episodio(idS1, "Piloto", (short) 1, "2008-01-20", 58, "Walter inicia sua jornada."));
    arqEpisodios.create(new Episodio(idS1, "Cat's in the Bag...", (short) 1, "2008-01-27", 48, "Consequências do primeiro ato."));
    arqEpisodios.create(new Episodio(idS1, "...And the Bag's in the River", (short) 1, "2008-02-10", 48, "Primeiro dilema moral."));

    arqEpisodios.create(new Episodio(idS2, "O desaparecimento de Will Byers", (short) 1, "2016-07-15", 47, "Will desaparece misteriosamente."));
    arqEpisodios.create(new Episodio(idS2, "A louca da rua Maple", (short) 1, "2016-07-15", 55, "Onze é encontrada."));

    arqEpisodios.create(new Episodio(idS3, "The Name of the Game", (short) 1, "2019-07-26", 59, "Hughie entra no time."));

    System.out.println("Base de dados populada com sucesso!\n");

  } catch (Exception e) {
    System.out.println("Erro ao popular dados:");
    e.printStackTrace();
  }
}

}
