package app.servico;

import app.modelo.*;
import app.modelo.enums.*;
import app.repositorio.*;
import java.util.List;

public class ReservaServico {
    private final ReservaRepositorio reservaRepo = new ReservaRepositorio();
    private final EspacoRepositorio espacoRepo = new EspacoRepositorio();
    private final PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();

    public Reserva salvar(Reserva reserva) throws Exception {
        if (reserva.getEspaco() == null) {
            throw new Exception("Reserva deve ter um espaço definido.");
        }

        boolean estaDisponivel = espacoRepo.buscarDisponiveis(reserva.getDataHoraInicio(), reserva.getDataHoraFim()).contains(reserva.getEspaco());
        if (!estaDisponivel)
            throw new Exception("Espaço indisponível no período selecionado.");

        double duracao = java.time.Duration.between(reserva.getDataHoraInicio(), reserva.getDataHoraFim()).toHours();
        reserva.setValorTotal(reserva.getEspaco().getPrecoDiaria() * (duracao / 24.0));

        reserva.setStatusReserva(StatusReserva.PENDENTE);
        reserva.setStatusPagamento(StatusPagamento.AGUARDANDO_SINAL);

        reservaRepo.salvar(reserva);
        return reserva;
    }

    public List<Reserva> listar() { 
        return reservaRepo.listar(); 
    }

    public Reserva buscarPorId(Long reservaId) { 
        return reservaRepo.buscarPorId(reservaId); 
    }

    public void atualizar(Reserva reserva) {
        reservaRepo.atualizar(reserva); 
    }

    public void atualizarParcialmente(Long id, Reserva reserva) {
        reservaRepo.atualizarParcialmente(id, reserva); 
    }

    public void remover(Long reservaId) { 
        reservaRepo.remover(reservaId); 
    }

    public void adicionarPagamento(Long reservaId, Pagamento pagamento) throws Exception {
        Reserva reserva = reservaRepo.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva não existe");
        }

        pagamento.setReserva(reserva);
        reserva.getPagamentos().add(pagamento);
        pagamentoRepo.salvar(pagamento);
        reservaRepo.atualizar(reserva);
    }
}
