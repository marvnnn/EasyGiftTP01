package Entidades;

import java.time.LocalDate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import aed3.*;

public class Lista implements Registro{
    private int id;
    private String name;
    private String description;
    private LocalDate createDate;
    private LocalDate endDate;

    public Lista() {
        this(-1, "", "", LocalDate.now(), LocalDate.now());
    }

    public Lista(String name, String description, LocalDate createDate, LocalDate endDate) {
        this(-1, name, description, createDate, endDate);
    }

    public Lista(int id, String name, String description, LocalDate createDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.description = dis.readUTF();

        // Reconstruindo as datas a partir do epochDay gravado como int
        this.createDate = java.time.LocalDate.ofEpochDay(dis.readInt());
        this.endDate = java.time.LocalDate.ofEpochDay(dis.readInt());

        dis.close();
        bais.close();
    }

    public byte[] toByteArray() throws java.io.IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeUTF(description);
        dos.writeInt((int)this.createDate.toEpochDay());
        dos.writeInt((int)this.endDate.toEpochDay());
        return baos.toByteArray();
    }

    // Getter id

    public int getId() {
        return this.id;
    }
}
