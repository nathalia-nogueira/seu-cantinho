package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import app.modelo.Filial;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {
    // Atributos
    @OneToOne
    @JoinColumn(name = "filialId", unique = true)
    private Filial filial;
}