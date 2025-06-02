
import java.lang.reflect.Constructor;
import java.util.Scanner;

import aeds3.ArvoreBMais;
import aeds3.ParIntInt;
import controle.ControleAtores;
import controle.ControleEpisodios;
import controle.ControleIndices;
import controle.ControleSeries;
import entidades.Ator;
import entidades.Episodio;
import entidades.Serie;
import modelo.ArquivoAtor;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;

public class PrincipalFlix {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            ArquivoSeries arqSeries = new ArquivoSeries();
            ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
            ArquivoAtor arqAtor = new ArquivoAtor();

            Constructor<ParIntInt> construtor = ParIntInt.class.getConstructor();
            ArvoreBMais<ParIntInt> serieAtores = new ArvoreBMais<>(construtor, 4, "dados/serie-atores.db");
            ArvoreBMais<ParIntInt> atorSeries = new ArvoreBMais<>(construtor, 4, "dados/ator-series.db");

            ControleIndices controleIndices = new ControleIndices();

            ControleSeries controleSeries = new ControleSeries(arqSeries, arqEpisodios, arqAtor, serieAtores, atorSeries, controleIndices);
            ControleAtores controleAtores = new ControleAtores(arqAtor, arqSeries, atorSeries, controleIndices);

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
                    case 1 ->
                        controleSeries.menu();
                    case 2 -> {
                        System.out.print("Digite o ID da série: ");
                        int idSerie = Integer.parseInt(sc.nextLine());
                        ControleEpisodios controleEpisodios = new ControleEpisodios(arqEpisodios, idSerie, controleIndices);
                        controleEpisodios.menu();
                    }
                    case 3 ->
                        controleAtores.menu();
                    case 9 ->
                        popularDados(controleIndices);
                }
            } while (opc != 0);

            System.out.println("Programa finalizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void popularDados(ControleIndices controleIndices) {
        System.out.println("\nPopulando a base de dados... Isso pode levar um momento.");
        try {
            ArquivoSeries arqSeries = new ArquivoSeries();
            ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
            ArquivoAtor arqAtor = new ArquivoAtor();

            Constructor<ParIntInt> construtor = ParIntInt.class.getConstructor();
            ArvoreBMais<ParIntInt> serieAtores = new ArvoreBMais<>(construtor, 4, "dados/serie-atores.db");
            ArvoreBMais<ParIntInt> atorSeries = new ArvoreBMais<>(construtor, 4, "dados/ator-series.db");

            // --- Atores ---
            System.out.println("Criando e indexando atores...");
            Ator a1 = new Ator("Bryan Cranston");
            arqAtor.create(a1);
            controleIndices.indexarAtor(a1);
            Ator a2 = new Ator("Aaron Paul");
            arqAtor.create(a2);
            controleIndices.indexarAtor(a2);
            Ator a3 = new Ator("Millie Bobby Brown");
            arqAtor.create(a3);
            controleIndices.indexarAtor(a3);
            Ator a4 = new Ator("Finn Wolfhard");
            arqAtor.create(a4);
            controleIndices.indexarAtor(a4);
            Ator a5 = new Ator("Karl Urban");
            arqAtor.create(a5);
            controleIndices.indexarAtor(a5);
            Ator a6 = new Ator("Jack Quaid");
            arqAtor.create(a6);
            controleIndices.indexarAtor(a6);

            //--- Séries ---
            System.out.println("Criando e indexando séries...");
            Serie s1 = new Serie("Breaking Bad", (short) 2008, "Um professor de química vira traficante.", "Netflix");
            arqSeries.create(s1);
            controleIndices.indexarSerie(s1);

            Serie s2 = new Serie("Stranger Things", (short) 2016, "Crianças enfrentam o Mundo Invertido.", "Netflix");
            arqSeries.create(s2);
            controleIndices.indexarSerie(s2);

            Serie s3 = new Serie("The Boys", (short) 2019, "Super-heróis corruptos enfrentam vigilantes.", "Prime Video");
            arqSeries.create(s3);
            controleIndices.indexarSerie(s3);

            // --- Vincular atores às séries ---
            System.out.println("Vinculando atores e séries...");
            serieAtores.create(new ParIntInt(s1.getID(), a1.getID()));
            serieAtores.create(new ParIntInt(s1.getID(), a2.getID()));
            atorSeries.create(new ParIntInt(a1.getID(), s1.getID()));
            atorSeries.create(new ParIntInt(a2.getID(), s1.getID()));

            serieAtores.create(new ParIntInt(s2.getID(), a3.getID()));
            serieAtores.create(new ParIntInt(s2.getID(), a4.getID()));
            atorSeries.create(new ParIntInt(a3.getID(), s2.getID()));
            atorSeries.create(new ParIntInt(a4.getID(), s2.getID()));

            serieAtores.create(new ParIntInt(s3.getID(), a5.getID()));
            serieAtores.create(new ParIntInt(s3.getID(), a6.getID()));
            atorSeries.create(new ParIntInt(a5.getID(), s3.getID()));
            atorSeries.create(new ParIntInt(a6.getID(), s3.getID()));

            // --- Episódios ---
            System.out.println("Criando e indexando episódios...");

            Episodio ep1 = new Episodio(s1.getID(), "Piloto", (short) 1, "2008-01-20", 58, "Walter inicia sua jornada.");
            arqEpisodios.create(ep1);
            controleIndices.indexarEpisodio(ep1);

            Episodio ep2 = new Episodio(s1.getID(), "Cat's in the Bag...", (short) 1, "2008-01-27", 48, "Consequências do primeiro ato.");
            arqEpisodios.create(ep2);
            controleIndices.indexarEpisodio(ep2);

            Episodio ep3 = new Episodio(s1.getID(), "...And the Bag's in the River", (short) 1, "2008-02-10", 48, "Primeiro dilema moral.");
            arqEpisodios.create(ep3);
            controleIndices.indexarEpisodio(ep3);

            Episodio ep4 = new Episodio(s2.getID(), "O desaparecimento de Will Byers", (short) 1, "2016-07-15", 47, "Will desaparece misteriosamente.");
            arqEpisodios.create(ep4);
            controleIndices.indexarEpisodio(ep4);

            Episodio ep5 = new Episodio(s2.getID(), "A louca da rua Maple", (short) 1, "2016-07-15", 55, "Onze é encontrada.");
            arqEpisodios.create(ep5);
            controleIndices.indexarEpisodio(ep5);

            Episodio ep6 = new Episodio(s3.getID(), "The Name of the Game", (short) 1, "2019-07-26", 59, "Hughie entra no time.");
            arqEpisodios.create(ep6);
            controleIndices.indexarEpisodio(ep6);

            System.out.println("\nBase de dados populada e indexada com sucesso!");

        } catch (Exception e) {
            System.err.println("\nERRO ao popular dados:");
            e.printStackTrace();
        }
    }
}
