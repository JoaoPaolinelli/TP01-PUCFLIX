package aeds3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdId implements RegistroArvoreBMais<ParIdId> {
  int id1;
  int id2;

  public ParIdId() {
    this(-1, -1);
  }

  public ParIdId(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  public int getId1() {
    return id1;
  }

  public int getId2() {
    return id2;
  }

  public void setId1(int id1) {
    this.id1 = id1;
  }

  public void setId2(int id2) {
    this.id2 = id2;
  }

  @Override
  public int compareTo(ParIdId o) {
      if (this.id1 != o.id1)
          return Integer.compare(this.id1, o.id1);
      return Integer.compare(this.id2, o.id2);
  }
  

  
  public int getTamanho() {
    return 8; // dois inteiros
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(ba));
    this.id1 = dis.readInt();
    this.id2 = dis.readInt();
  }

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
    dos.writeInt(this.id1);
    dos.writeInt(this.id2);
    return bos.toByteArray();
  }

  @Override
  public String toString() {
    return "(" + id1 + ", " + id2 + ")";
  }

  @Override
  public ParIdId clone() {
    return new ParIdId(this.id1, this.id2);
  }

  @Override
  public short size() {
  return 8; // dois inteiros de 4 bytes cada
}

}
