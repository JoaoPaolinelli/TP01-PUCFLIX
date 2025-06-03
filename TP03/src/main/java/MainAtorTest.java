import entidades.Ator;
import modelo.ArquivoAtor;

public class MainAtorTest {
  public static void main(String[] args) {
    try {
      ArquivoAtor arq = new ArquivoAtor();

      int id = arq.create(new Ator("Wagner Moura"));
      System.out.println("Criado: " + arq.read(id));

      Ator a = arq.read(id);
      a.setNome("Selton Mello");
      arq.update(a);
      System.out.println("Atualizado: " + arq.read(id));

      arq.delete(id);
      System.out.println("Removido: " + arq.read(id));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
