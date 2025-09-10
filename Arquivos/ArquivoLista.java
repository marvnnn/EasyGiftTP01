package Arquivos;

import aed3.*;
import java.io.RandomAccessFile;
import Entidades.*;
import java.io.*;

import Entidades.Usuario;

public class ArquivoLista extends Arquivo{
    RandomAccessFile arquivo;

    public ArquivoLista() throws Exception {
        super("lista", Lista.class.getConstructor());
        if(arquivo.length()<4) {
            arquivo.writeInt(0);
        }
    }
}