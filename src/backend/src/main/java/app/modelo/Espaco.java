package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import app.modelo.Endereco;
import app.modelo.Filial;
import app.modelo.Reserva;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@Table(name = "espaco")
public class Espaco {
    // Atributos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @ElementCollection
    @CollectionTable(name = "espacoFotos", joinColumns = @JoinColumn(name = "espacoId"))
    @Column(name = "fotoArq")
    private List<String> fotos;

    @Column(nullable = false)
    private Long capacidade;

    @Column(nullable = false)
    private double precoDiaria;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enderecoId", unique = true, nullable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "filialId", nullable = false)
    private Filial filial;

    @OneToMany(mappedBy = "espaco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}