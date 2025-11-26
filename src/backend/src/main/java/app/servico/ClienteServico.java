package app.servico;

import app.modelo.*;
import app.repositorio.*;
import app.servico.*;

public class ClienteServico extends UsuarioServico {
    private final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
    
    public void salvar(Cliente cliente) throws Exception {
        super.validarUsuario(cliente);
        usuarioRepo.salvar(cliente);
    }
}