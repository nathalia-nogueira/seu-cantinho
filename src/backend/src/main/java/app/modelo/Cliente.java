package app.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import app.modelo.Reserva;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Persistência
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {
    // Atributos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Reserva> reservas; 
}