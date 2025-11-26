package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;
import java.time.LocalDateTime;

public class EspacoServico {
    private final EspacoRepositorio espacoRepo = new EspacoRepositorio();

    public void salvar(Espaco espaco) throws Exception {
        if (espaco.getNome() == null || espaco.getNome().isBlank()) {
            throw new Exception("Espaço precisa de um nome."); 
        }

        if (espaco.getPrecoDiaria() <= 0) {
            throw new Exception("Preço da diária inválido.");
        }
        espacoRepo.salvar(espaco);
    }

    public List<Espaco> listar() {
        return espacoRepo.listar();
    }

    public Espaco buscarPorId(Long espacoId) {
        return espacoRepo.buscarPorId(espacoId);
    }

    public void atualizar(Espaco espaco) {
        espacoRepo.atualizar(espaco);
    }

    public void atualizarParcialmente(Long id, Espaco espaco) {
        espacoRepo.atualizarParcialmente(id, espaco);
    }

    public void remover(Long espacoId) {
        espacoRepo.remover(espacoId); 
    }

    public List<Espaco> buscarDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        return espacoRepo.buscarDisponiveis(inicio, fim);
    }
}
