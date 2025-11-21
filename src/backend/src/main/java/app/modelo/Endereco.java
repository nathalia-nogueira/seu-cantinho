package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime; 
import java.util.List;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@Table(name = "endereco")
public class Endereco {
    // Atributos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private Integer numero;
}