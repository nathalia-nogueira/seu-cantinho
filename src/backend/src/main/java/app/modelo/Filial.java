package app.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime; 
import java.util.List;
import app.modelo.Endereco;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filial {
    // Atributos
    private Long id;
    private String nome;
    private Endereco endereco;
    private List<Espaco> espacos;
}