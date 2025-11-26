package app.servico;

import app.modelo.*;
import app.repositorio.*;
import java.util.List;

public class PagamentoServico {
    private final PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();

    public void salvar(Pagamento pagamento) throws Exception {
        if (pagamento.getValor() <= 0) {
            throw new Exception("Valor do pagamento deve ser positivo.");
        }
        
        pagamentoRepo.salvar(pagamento);
    }

    public List<Pagamento> listar() { 
        return pagamentoRepo.listar(); 
    }

    public Pagamento buscarPorId(Long pagamentoId) { 
        return pagamentoRepo.buscarPorId(pagamentoId); 
    }

    public void atualizar(Pagamento pagamento) { 
        pagamentoRepo.atualizar(pagamento); 
    }

    public void atualizarParcialmente(Long id, Pagamento pagamento) { 
        pagamentoRepo.atualizarParcialmente(id, pagamento); 
    }

    public void remover(Long pagamentoId) { 
        pagamentoRepo.remover(pagamentoId); 
    }

    public double totalPorReserva(Long reservaId) {
        return pagamentoRepo.somarPagamentosPorReserva(reservaId);
    }
}
