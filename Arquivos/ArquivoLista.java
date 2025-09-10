package Arquivos;

import aed3.*;
import Entidades.*;
import java.io.*;

import Entidades.Usuario;

public class ArquivoLista implements Arquivo{
    RandomAccessFile arquivo;

    public ArquivoLista() throws Exception {
        arquivo = new RandomAccessFile("./Dados/list.db", "rw");
        if(arquivo.length()<4) {
            arquivo.writeInt(0);
        }
    }

    public void create(Lista list) throws Exception {
        arquivo.seek(0);
        int novoId = arquivo.readInt()+1;
        arquivo.seek(0);
        arquivo.writeInt(novoId);
        novoId = list.getId();

        arquivo.seek(arquivo.length());
        byte vb [] = list.toByteArray();
        arquivo.writeShort(vb.length);
        arquivo.write(vb);
    }
}