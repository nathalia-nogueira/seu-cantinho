package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class ClienteServico {
    protected final ClienteRepositorio clienteRepo = new ClienteRepositorio();

    public void salvar(Cliente cliente) throws Exception {
        // validarCliente(cliente);
        clienteRepo.salvar(cliente);
    }

    public List<Cliente> listar() { 
        return clienteRepo.listar(); 
    }

    public Cliente buscarPorId(Long clienteId) { 
        return clienteRepo.buscarPorId(clienteId); 
    }

    public void atualizar(Cliente cliente) { 
        clienteRepo.atualizar(cliente); 
    }

    public void atualizarParcialmente(Long id, Cliente cliente) {
        clienteRepo.atualizarParcialmente(id, cliente);
    }

    public void remover(Long clienteId) { 
        clienteRepo.remover(clienteId); 
    }

    public void validarCliente(Cliente cliente) throws Exception {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new Exception("Nome é obrigatório.");
        }

        if (cliente.getCpf() == null) {
            throw new Exception("CPF é obrigatório.");
        }

        if (cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
            throw new Exception("E-mail inválido.");
        }
    }
}