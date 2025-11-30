package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class UsuarioServico {
    protected final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();

    public void salvar(Usuario usuario) throws Exception {
        validarUsuario(usuario);
        usuarioRepo.salvar(usuario);
    }

    public List<Usuario> listar() { 
        return usuarioRepo.listar(); 
    }

    public Usuario buscarPorId(Long usuarioId) { 
        return usuarioRepo.buscarPorId(usuarioId); 
    }

    public void atualizar(Usuario usuario) { 
        usuarioRepo.atualizar(usuario); 
    }

    public void atualizarParcialmente(Long id, Usuario usuario) {
        usuarioRepo.atualizarParcialmente(id, usuario);
    }

    public void remover(Long usuarioId) { 
        usuarioRepo.remover(usuarioId); 
    }

    public void validarUsuario(Usuario usuario) throws Exception {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new Exception("Nome é obrigatório.");
        }

        if (usuario.getCpf() == null) {
            throw new Exception("CPF é obrigatório.");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new Exception("E-mail inválido.");
        }
    }

    public List<Usuario> listarPorTipo(Class<? extends Usuario> tipo) {
        return usuarioRepo.listarPorTipo(tipo);
    }
}