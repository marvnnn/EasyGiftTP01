package Arquivos;

import Entidades.*;
import java.io.*;

import aed3.Arquivo;
import aed3.HashExtensivel;


public class ArquivoLista extends Arquivo<Lista>{
    RandomAccessFile arquivo;

    public ArquivoLista() throws Exception {
        super("lista", Lista.class.getConstructor());
        if(arquivo.length()<4) {
            arquivo.writeInt(0);
        }
    }
}