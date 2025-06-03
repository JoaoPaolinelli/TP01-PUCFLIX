package entidades;

import aeds3.EntidadeArquivo;
import java.io.*;

public class Ator implements EntidadeArquivo {
  private int id;
  private String nome;

  public Ator() {
    this.id = -1;
  }

  public Ator(String nome) {
    this.id = -1;
    this.nome = nome;
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

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream ba = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(ba);

    out.writeInt(id);
    out.writeUTF(nome);

    return ba.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bb = new ByteArrayInputStream(ba);
    DataInputStream in = new DataInputStream(bb);

    id = in.readInt();
    nome = in.readUTF();
  }

  @Override
  public String toString() {
    return "Ator [id=" + id + ", nome=" + nome + "]";
  }
}
