package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class EnderecoServico {
    private final EnderecoRepositorio enderecoRepo = new EnderecoRepositorio();

    public void salvar(Endereco endereco) throws Exception {
        if (endereco.getCidade() == null || endereco.getEstado() == null) {
            throw new Exception("Endereço deve conter cidade e estado.");
        }

        enderecoRepo.salvar(endereco);
    }

    public List<Endereco> listar() { 
        return enderecoRepo.listar(); 
    }

    public Endereco buscarPorId(Long enderecoId) { 
        return enderecoRepo.buscarPorId(enderecoId); 
    }

    public void atualizar(Endereco endereco) { 
        enderecoRepo.atualizar(endereco); 
    }


    public void atualizarParcialmente(Long id, Endereco endereco) { 
        enderecoRepo.atualizarParcialmente(id, endereco); 
    }

    public void remover(Long enderecoId) { 
        enderecoRepo.remover(enderecoId); 
    }
}
