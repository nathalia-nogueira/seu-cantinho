package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity 
@Table(name = "pagamento")
public class Pagamento {
    // Atributos

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "reservaId", nullable = false)
    private Reserva reserva;
}