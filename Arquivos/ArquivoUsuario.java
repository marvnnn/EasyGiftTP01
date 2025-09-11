package Arquivos;

import Entidades.*;
import java.io.*;

import Entidades.Usuario;
import aed3.Arquivo;

public class ArquivoUsuario extends Arquivo<Usuario> {

    private RandomAccessFile arquivo;

    public ArquivoUsuario() throws Exception {
        super("usuario", Usuario.class.getConstructor());

        // Inicializa o RandomAccessFile
        arquivo = new RandomAccessFile("usuario.db", "rw");

        // Se o arquivo estiver vazio, escreve 0 no início (quantidade de registros)
        if (arquivo.length() < 4) {
            arquivo.writeInt(0);
        }

    }
}