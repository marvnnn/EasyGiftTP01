package Arquivos;

import Entidades.*;
import java.io.*;

import Entidades.Usuario;
import aed3.Arquivo;
import aed3.HashExtensivel;
import aed3.ParCPFID;

public class ArquivoUsuario extends Arquivo<Usuario> {

    public HashExtensivel<ParCPFID> indiceCPF;

    public ArquivoUsuario() throws Exception {
        super("usuario", Usuario.class.getConstructor());

        // inicializa índice CPF → ID
        indiceCPF = new HashExtensivel<>(
            ParCPFID.class.getConstructor(),
            4,
            "usuario.cpf.idx",
            "usuario.cpf.bkt"
        );
    }

    public int create(Usuario u) throws Exception {
        int id = super.create(u); // grava no arquivo principal e índice direto
        
        indiceCPF.create(new ParCPFID(u.getCPF(), id)); // grava no índice CPF
        return id;
    }

    public Usuario readByCPF(String cpf) throws Exception {
        // cria um objeto "fake" só pra calcular a chave corretamente
        ParCPFID chaveBusca = new ParCPFID(cpf, -1);

        // busca no índice usando a chave (que vem de hashCode do ParCPFID)
        ParCPFID pci = indiceCPF.read(chaveBusca.hashCode());

        if (pci != null) {
            return read(pci.getId()); // não precisa do -1
        }
            return null;
    }
}
