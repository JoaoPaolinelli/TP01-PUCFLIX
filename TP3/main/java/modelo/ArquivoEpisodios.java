package modelo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import aeds3.Arquivo;
import aeds3.ArvoreBMais;
import aeds3.ParIdId;
import entidades.Episodio;

public class ArquivoEpisodios extends Arquivo<Episodio> {

    private ArvoreBMais<ParIdId> indiceSerieEpisodio;

    public ArquivoEpisodios() throws Exception {
        super("episodios", (Constructor<Episodio>) (Constructor<?>) Episodio.class.getConstructor());
        indiceSerieEpisodio = new ArvoreBMais<ParIdId>(ParIdId.class.getConstructor(), 4, "dados/episodios/serie-episodio.db");
    }

    @Override
    public int create(Episodio e) throws Exception {
        int id = super.create(e);
        indiceSerieEpisodio.create(new ParIdId(e.getIdSerie(), id));
        return id;
    }

    public boolean delete(int id) throws Exception {
        Episodio e = this.read(id);
        if (e != null) {
            indiceSerieEpisodio.delete(new ParIdId(e.getIdSerie(), id));
        }
        return super.delete(id);
    }

    

    public int[] buscarEpisodiosPorSerie(int idSerie) throws Exception {
      List<Integer> encontrados = new ArrayList<>();
  
      int ultimoID;
      try {
        ultimoID = this.getUltimoID();
      } catch (Exception e) {
        return new int[0];
      }
  
      for (int i = 1; i <= ultimoID; i++) {
        Episodio ep = this.read(i);
        if (ep != null && ep.getIdSerie() == idSerie) {
          encontrados.add(ep.getID());
        }
      }
  
      return encontrados.stream().mapToInt(Integer::intValue).toArray();
    }

    

}
