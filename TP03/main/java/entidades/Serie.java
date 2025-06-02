package entidades;

import aeds3.EntidadeArquivo;
import java.io.*;

public class Serie implements EntidadeArquivo{
  private int id;
  private String nome;
  private short anoLancamento;
  private String sinopse;
  private String streaming;

  public Serie() {
    this.id = -1;
  }

  public Serie(String nome, short anoLancamento, String sinopse, String streaming) {
    this.id = -1;
    this.nome = nome;
    this.anoLancamento = anoLancamento;
    this.sinopse = sinopse;
    this.streaming = streaming;
  }

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public short getAnoLancamento() {
    return anoLancamento;
  }

  public void setAnoLancamento(short anoLancamento) {
    this.anoLancamento = anoLancamento;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public String getStreaming() {
    return streaming;
  }

  public void setStreaming(String streaming) {
    this.streaming = streaming;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream ba = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(ba);

    out.writeInt(id);
    out.writeUTF(nome);
    out.writeShort(anoLancamento);
    out.writeUTF(sinopse);
    out.writeUTF(streaming);

    return ba.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bb = new ByteArrayInputStream(ba);
    DataInputStream in = new DataInputStream(bb);

    id = in.readInt();
    nome = in.readUTF();
    anoLancamento = in.readShort();
    sinopse = in.readUTF();
    streaming = in.readUTF();
  }
}


