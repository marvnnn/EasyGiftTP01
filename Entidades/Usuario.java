package Entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import aed3.*;

public class Usuario implements Registro  {
    private int id;
    private String cpf;
    private String passwordHash;
    private String name;
    private String email;
    private String secretQuestion;
    private String secretAnswer;

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Usuario() {
        this(-1, "","", "", "", "", "");
    }

    public Usuario(String cpf, String passwordHash, String name, String email, String secretQuestion, String secretAnswer) {
        this(-1, cpf, passwordHash, name, email, secretQuestion, secretAnswer);
    }

    public Usuario(int id, String cpf, String passwordHash, String name, String email, String secretQuestion, String secretAnswer) {
        if(cpf.length() != 11) {
            throw new RuntimeException("CPF inválido.");
        }
        this.id = id;
        this.cpf = cpf;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }

    public byte[] toByteArray() throws java.io.IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.write(this.cpf.getBytes());
        dos.writeUTF(this.passwordHash);
        dos.writeUTF(this.name);
        dos.writeUTF(this.email);
        dos.writeUTF(this.secretQuestion);
        dos.writeUTF(this.secretAnswer);
        return baos.toByteArray();
    }

   public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        //byte[] cpf = new byte[11];
        this.id              = dis.readInt();
        this.name            = dis.readUTF();
        this.cpf             = dis.readUTF();
        this.passwordHash    = dis.readUTF();
        this.email           = dis.readUTF();
        this.secretQuestion  = dis.readUTF();
        this.secretAnswer    = dis.readUTF();
    }


    public void setId(int i) {
        this.id = i;
    }
   

    public int getId() {
        return this.id;
    }
}