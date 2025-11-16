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
public class Endereco {
    // Atributos
    private String estado;
    private String cidade;
    private String rua;
    private Integer numero;
}