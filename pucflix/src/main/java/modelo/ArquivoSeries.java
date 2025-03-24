package modelo;

import java.lang.reflect.Constructor;

import aeds3.Arquivo;
import entidades.Serie;

public class ArquivoSeries extends Arquivo<Serie> {
  public ArquivoSeries() throws Exception {
    super("series_db", (Constructor<Serie>) (Constructor<?>) Serie.class.getConstructor());  }

  // Você pode adicionar métodos específicos para Series aqui, se necessário.
}