package app.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime; 
import java.util.List;
import app.modelo.enums.StatusReserva;
import app.modelo.enums.StatusPagamento;
import app.modelo.Cliente;
import app.modelo.Espaco;
import app.modelo.Pagamento;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    // Atributos
    private Long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private double sinal;
    private double valorTotal;
    private StatusReserva statusReserva;
    private StatusPagamento statusPagamento;
    private Cliente cliente;
    private Espaco espaco;
    private List<Pagamento> pagamentos;
}