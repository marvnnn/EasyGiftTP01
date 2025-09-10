package Entidades;

import java.time.LocalDate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Lista {
    private int id;
    private int idUsuario;
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

    public byte[] toByteArray() throws Exception {
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
