package app.servico;

import app.modelo.*;
import app.modelo.enums.*;
import app.repositorio.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaServico {
    private final ReservaRepositorio reservaRepo = new ReservaRepositorio();
    private final EspacoRepositorio espacoRepo = new EspacoRepositorio();
    private final PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();

    private final PagamentoServico pagamentoServico = new PagamentoServico();

    public Reserva salvar(Reserva reserva) throws Exception {
        if (reserva.getEspaco() == null) {
            throw new Exception("Reserva deve ter um espaço definido.");
        }

        List<Espaco> espacosDisponiveis = espacoRepo.buscarDisponiveis(reserva.getDataHoraInicio(), reserva.getDataHoraFim()); 
        boolean estaDisponivel = espacoEstaDisponivel(reserva, espacosDisponiveis);

        if (!estaDisponivel) {
            System.out.println("Espaço não disponível");
            throw new Exception("Espaço indisponível no período selecionado.");
        }

        Espaco espaco = espacoRepo.buscarPorId(reserva.getEspaco().getId());        
        double duracaoEmMinutos = java.time.Duration.between(reserva.getDataHoraInicio(), reserva.getDataHoraFim()).toMinutes();
        double precoDiaria = espaco.getPrecoDiaria();

        reserva.setValorTotal((precoDiaria * duracaoEmMinutos / 1440.0) + reserva.getSinal());

        reserva.setStatusReserva(StatusReserva.PENDENTE);
        reserva.setStatusPagamento(StatusPagamento.AGUARDANDO_SINAL);

        reservaRepo.salvar(reserva);
        return reserva;
    }

    public boolean espacoEstaDisponivel(Reserva reserva, List<Espaco> espacosDisponiveis) {
        for (Espaco e : espacosDisponiveis) {
            if (e.getId().equals(reserva.getEspaco().getId())) {
                return true;
            }
        }
        return false;
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

    public String processarPagamento(Reserva reserva, Double valorPago) {
        Pagamento pagamento = new Pagamento();
        pagamento.setReserva(reserva);
        pagamento.setValor(valorPago);
        pagamento.setData(LocalDateTime.now());
        reserva.getPagamentos().add(pagamento);
    
        double totalPago = pagamentoServico.totalPorReserva(reserva.getId());

        if (totalPago >= reserva.getValorTotal()) {
            reserva.setStatusPagamento(StatusPagamento.QUITADO);
            reserva.setStatusReserva(StatusReserva.CONFIRMADA);
        
            return "Pagamento total realizado. Reserva confirmada.";
        } else if (totalPago >= reserva.getSinal()) {
            reserva.setStatusPagamento(StatusPagamento.SINAL_PAGO);
            reserva.setStatusReserva(StatusReserva.CONFIRMADA);
        
            return "Pagamento parcial realizado. Reserva confirmada. Valor pendente: " + (reserva.getValorTotal() - totalPago);
        } else {
            reserva.setStatusPagamento(StatusPagamento.AGUARDANDO_SINAL);
            reserva.setStatusReserva(StatusReserva.PENDENTE);
        
            return "Sinal recebido, mas valor insuficiente. Valor pendente para confirmação: " + (reserva.getSinal() - totalPago);
        }
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
