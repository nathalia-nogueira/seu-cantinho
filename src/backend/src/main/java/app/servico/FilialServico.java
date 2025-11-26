package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class FilialServico {
    private final FilialRepositorio filialRepo = new FilialRepositorio();

    public void salvar(Filial filial) throws Exception {
        if (filial.getEndereco() == null)
            throw new Exception("Filial precisa de endereço.");
        
        filialRepo.salvar(filial);
    }

    public List<Filial> listar() { 
        return filialRepo.listar(); 
    }

    public Filial buscarPorId(Long filialId) { 
        return filialRepo.buscarPorId(filialId); 
    }

    public void atualizar(Filial filial) { 
        filialRepo.atualizar(filial); 
    }

    public void atualizarParcialmente(Long id, Filial filial) { 
        filialRepo.atualizarParcialmente(id, filial); 
    }

    public void remover(Long filialId) { 
        filialRepo.remover(filialId); 
    }

    public List<Espaco> listarEspacos(Long filialId) {
        return filialRepo.listarEspacosPorFilial(filialId);
    }
}
