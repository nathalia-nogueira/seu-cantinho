package app.modelo;

import lombok.*;
import jakarta.persistence.*;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Usuario {
    // Atributos
    @Id                                             
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;
}