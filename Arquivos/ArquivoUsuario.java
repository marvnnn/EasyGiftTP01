package Arquivos;

import Entidades.*;
import java.io.*;

import Entidades.Usuario;
import aed3.Arquivo;

public class ArquivoUsuario extends Arquivo{

    RandomAccessFile arquivo;}

    public ArquivoUsuario() throws Exception {
        super("usuario", Usuario.class.getConstructor());
        if(arquivo.length()<4) {
            arquivo.writeInt(0);
        }
    }
}