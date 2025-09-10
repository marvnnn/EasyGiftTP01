package Entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

public class Usuario {
    private int id;
    private String cpf;
    private int passwordHash;
    private String name;
    private String email;
    private String secretQuestion;
    private String secretAnswer;

    public Usuario() {
        this(-1, "",-1, "", "", "", "");
    }

    public Usuario(String cpf, int passwordHash, String name, String email, String secretQuestion, String secretAnswer) {
        this(-1, cpf, passwordHash, name, email, secretQuestion, secretAnswer);
    }

    public Usuario(int id, String cpf, int passwordHash, String name, String email, String secretQuestion, String secretAnswer) {
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

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.write(this.cpf.getBytes());
        dos.writeInt(this.passwordHash);
        dos.writeUTF(this.name);
        dos.writeUTF(this.email);
        dos.writeUTF(this.secretQuestion);
        dos.writeUTF(this.secretAnswer);
        return baos.toByteArray();
    }

    // Getter id

    public int getId() {
        return this.id;
    }
}