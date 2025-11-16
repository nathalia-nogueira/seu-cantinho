package app.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    // Atributos
    private Long id;
    private double valor;
    private LocalDateTime data;
    private Reserva reserva;
}