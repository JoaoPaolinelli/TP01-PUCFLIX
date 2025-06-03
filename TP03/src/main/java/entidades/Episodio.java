
package entidades;

import aeds3.EntidadeArquivo;
import java.io.*;

public class Episodio implements  EntidadeArquivo{
  private int id;
  private int idSerie; // chave estrangeira
  private String nome;
  private short temporada;
  private String dataLancamento;
  private int duracao;
  private String sinopse;

  public Episodio() {
    this.id = -1;
  }

  public Episodio(int idSerie, String nome, short temporada, String dataLancamento, int duracao, String sinopse) {
    this.id = -1;
    this.idSerie = idSerie;
    this.nome = nome;
    this.temporada = temporada;
    this.dataLancamento = dataLancamento;
    this.duracao = duracao;
    this.sinopse = sinopse;
  }

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public int getIdSerie() {
    return idSerie;
  }

  public void setIdSerie(int idSerie) {
    this.idSerie = idSerie;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public short getTemporada() {
    return temporada;
  }

  public void setTemporada(short temporada) {
    this.temporada = temporada;
  }

  public String getDataLancamento() {
    return dataLancamento;
  }

  public void setDataLancamento(String dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public int getDuracao() {
    return duracao;
  }

  public void setDuracao(int duracao) {
    this.duracao = duracao;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream ba = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(ba);

    out.writeInt(id);
    out.writeInt(idSerie);
    out.writeUTF(nome);
    out.writeShort(temporada);
    out.writeUTF(dataLancamento);
    out.writeInt(duracao);
    out.writeUTF(sinopse);

    return ba.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bb = new ByteArrayInputStream(ba);
    DataInputStream in = new DataInputStream(bb);

    id = in.readInt();
    idSerie = in.readInt();
    nome = in.readUTF();
    temporada = in.readShort();
    dataLancamento = in.readUTF();
    duracao = in.readInt();
    sinopse = in.readUTF();
  }
}
