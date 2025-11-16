package app.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    // Atributos
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
}