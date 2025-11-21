package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime; 
import java.util.List;
import app.modelo.enums.*;
import app.modelo.*;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity 
@Table(name = "reserva")
public class Reserva {
    // Atributos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false)
    private double sinal;

    @Column(nullable = false)
    private double valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva statusReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)  
    private StatusPagamento statusPagamento;

    @ManyToOne
    @JoinColumn(name = "clienteId", nullable = false)
    private Cliente cliente;

    @ManyToOne 
    @JoinColumn(name = "espacoId", nullable = false)
    private Espaco espaco;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos;
}