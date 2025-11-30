package app.controlador;

import static spark.Spark.*;
import com.google.gson.*;
import app.modelo.Espaco;
import app.servico.EspacoServico;
import java.util.*;

public class EspacoControlador {
    private static final EspacoServico espacoServico = new EspacoServico();
    private static final Gson gson = new Gson();

    public static void rotas() {
        path("/espacos", () -> {
            get("", (req, res) -> {
                List<Espaco> espacos = espacoServico.listar();
                List<Map<String, Object>> resultado = new ArrayList<>();
                for (Espaco espaco : espacos) {
                    Map<String, Object> espacoMap = new HashMap<>();
                    espacoMap.put("id", espaco.getId());
                    espacoMap.put("nome", espaco.getNome());
                    espacoMap.put("descricao", espaco.getDescricao());
                    espacoMap.put("capacidade", espaco.getCapacidade());
                    espacoMap.put("precoDiaria", espaco.getPrecoDiaria());

                    Map<String, Object> filialMap = new HashMap<>();
                    if (espaco.getFilial() != null) {
                        filialMap.put("id", espaco.getFilial().getId());
                    }
                    espacoMap.put("filial", filialMap);

                    Map<String, Object> enderecoMap = new HashMap<>();
                    if (espaco.getEndereco() != null) {
                        enderecoMap.put("rua", espaco.getEndereco().getRua());
                        enderecoMap.put("numero", espaco.getEndereco().getNumero());
                        enderecoMap.put("cidade", espaco.getEndereco().getCidade());
                        enderecoMap.put("estado", espaco.getEndereco().getEstado());
                    }
                    espacoMap.put("endereco", enderecoMap);

                    resultado.add(espacoMap);
                }

                res.type("application/json");
                return new Gson().toJson(resultado);
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco espaco = espacoServico.buscarPorId(id);
                if (espaco == null) {
                    res.status(404);
                    return "{\"erro\": \"Usuário não encontrado\"}";
                }
                return gson.toJson(espaco);
            });

            post("", (req, res) -> {
                Espaco espaco = gson.fromJson(req.body(), Espaco.class);
                espacoServico.salvar(espaco);
                res.status(201);
                return gson.toJson(espaco);
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco espaco = gson.fromJson(req.body(), Espaco.class);
                espaco.setId(id);
                espacoServico.atualizar(espaco);
                return gson.toJson(espaco);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Espaco dadosNovos = gson.fromJson(req.body(), Espaco.class);
                espacoServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(espacoServico.buscarPorId(id));
            });

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                espacoServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
