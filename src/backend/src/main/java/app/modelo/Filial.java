package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime; 
import java.util.List;
import app.modelo.Endereco;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@Table(name = "filial")
public class Filial {
    // Atributos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enderecoId", unique = true, nullable = false)
    private Endereco endereco;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Espaco> espacos;
}