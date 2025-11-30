package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class AdministradorServico {
    protected final AdministradorRepositorio administradorRepo = new AdministradorRepositorio();

    public void salvar(Administrador administrador) throws Exception {
        validarAdministrador(administrador);
        administradorRepo.salvar(administrador);
    }

    public List<Administrador> listar() { 
        return administradorRepo.listar(); 
    }

    public Administrador buscarPorId(Long administradorId) { 
        return administradorRepo.buscarPorId(administradorId); 
    }

    public void atualizar(Administrador administrador) { 
        administradorRepo.atualizar(administrador); 
    }

    public void atualizarParcialmente(Long id, Administrador administrador) {
        administradorRepo.atualizarParcialmente(id, administrador);
    }

    public void remover(Long administradorId) { 
        administradorRepo.remover(administradorId); 
    }

    public void validarAdministrador(Administrador administrador) throws Exception {
        if (administrador.getNome() == null || administrador.getNome().isBlank()) {
            throw new Exception("Nome é obrigatório.");
        }

        if (administrador.getCpf() == null) {
            throw new Exception("CPF é obrigatório.");
        }

        if (administrador.getEmail() == null || !administrador.getEmail().contains("@")) {
            throw new Exception("E-mail inválido.");
        }
    }
}