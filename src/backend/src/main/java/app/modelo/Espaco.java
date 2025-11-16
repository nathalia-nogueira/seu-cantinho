package app.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import app.modelo.Endereco;
import app.modelo.Filial;
import app.modelo.Reserva;

// Criação de construtores, getters e setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Espaco {
    // Atributos
    private Long id;
    private String nome;
    private String descricao;
    private List<String> fotos;
    private Long capacidade;
    private double precoDiaria;
    private Endereco endereco;
    private Filial filial;
    private List<Reserva> reservas;
}