package app.servico;

import app.modelo.*;
import app.repositorio.*;
import app.servico.*;

public class AdministradorServico extends UsuarioServico {
    private final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
    
    public void salvar(Administrador administrador) throws Exception {
        super.validarUsuario(administrador);
        if (administrador.getFilial() == null) {
            throw new Exception("Administrador deve estar vinculado a uma filial");
        }

        usuarioRepo.salvar(administrador);
    }
}