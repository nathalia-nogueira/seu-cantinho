package app.controlador;

import static spark.Spark.*;
import com.google.gson.*;
import app.modelo.Reserva;
import app.servico.ReservaServico;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservaControlador {
    private static final ReservaServico reservaServico = new ReservaServico();
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        })
        .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        })
        .create();

    public static void rotas() {
        path("/reservas", () -> {
            get("", (req, res) -> {
                List<Reserva> reservas = reservaServico.listar();
                List<Map<String, Object>> resultado = new ArrayList<>();
                for (Reserva reserva : reservas) {
                    Map<String, Object> reservaMap = new HashMap<>();
                    reservaMap.put("id", reserva.getId());
                    reservaMap.put("dataHoraInicio", reserva.getDataHoraInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    reservaMap.put("dataHoraFim", reserva.getDataHoraFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    reservaMap.put("sinal", reserva.getSinal());
                    reservaMap.put("valorTotal", reserva.getValorTotal());
                    reservaMap.put("statusReserva", reserva.getStatusReserva());
                    reservaMap.put("statusPagamento", reserva.getStatusPagamento());

                    Map<String, Object> clienteMap = new HashMap<>();
                    if (reserva.getCliente() != null) {
                        clienteMap.put("id", reserva.getCliente().getId());
                    }
                    reservaMap.put("cliente", clienteMap);

                    Map<String, Object> espacoMap = new HashMap<>();
                    if (reserva.getEspaco() != null) {
                        espacoMap.put("id", reserva.getEspaco().getId());
                    }
                    reservaMap.put("espaco", espacoMap);

                    resultado.add(reservaMap);
                }

                res.type("application/json");
                return new Gson().toJson(resultado);
            });

            get("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva reserva = reservaServico.buscarPorId(id);
                if (reserva == null) {
                    res.status(404);
                    return "{\"erro\": \"Reserva não encontrada\"}";
                }

                Map<String, Object> reservaMap = new HashMap<>();
                reservaMap.put("id", reserva.getId());
                reservaMap.put("dataHoraInicio", reserva.getDataHoraInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                reservaMap.put("dataHoraFim", reserva.getDataHoraFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                reservaMap.put("sinal", reserva.getSinal());
                reservaMap.put("valorTotal", reserva.getValorTotal());
                reservaMap.put("statusReserva", reserva.getStatusReserva());
                reservaMap.put("statusPagamento", reserva.getStatusPagamento());

                Map<String, Object> clienteMap = new HashMap<>();
                if (reserva.getCliente() != null) {
                    clienteMap.put("id", reserva.getCliente().getId());
                }
                reservaMap.put("cliente", clienteMap);

                Map<String, Object> espacoMap = new HashMap<>();
                if (reserva.getEspaco() != null) {
                    espacoMap.put("id", reserva.getEspaco().getId());
                }
                reservaMap.put("espaco", espacoMap);
                
                return gson.toJson(reservaMap);
            });

            post("", (req, res) -> {
                Reserva reserva = gson.fromJson(req.body(), Reserva.class);
                reservaServico.salvar(reserva);

                Reserva r = reservaServico.buscarPorId(reserva.getId());
                String respostaJson = "{"
                    + "\"id\": " + r.getId() + ","
                    + "\"dataHoraInicio\": \"" + r.getDataHoraInicio() + "\","
                    + "\"dataHoraFim\": \"" + r.getDataHoraFim() + "\","
                    + "\"sinal\": " + r.getSinal() + ","
                    + "\"valorTotal\": " + r.getValorTotal() + ","
                    + "\"statusReserva\": \"" + r.getStatusReserva() + "\","
                    + "\"statusPagamento\": \"" + r.getStatusPagamento() + "\","
                    + "\"clienteId\": " + (r.getCliente() != null ? r.getCliente().getId() : "null") + ","
                    + "\"espacoId\": " + (r.getEspaco() != null ? r.getEspaco().getId() : "null")
                    + "}";

                res.status(201);
                res.type("application/json");
                return respostaJson;
            });

            put("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva reserva = gson.fromJson(req.body(), Reserva.class);
                reserva.setId(id);
                reservaServico.atualizar(reserva);
                return gson.toJson(reserva);
            });

            patch("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                Reserva dadosNovos = gson.fromJson(req.body(), Reserva.class);
                reservaServico.atualizarParcialmente(id, dadosNovos);
                return gson.toJson(reservaServico.buscarPorId(id));
            });

            patch("/:id/pagar", (req, res) -> {
                try {
                    Long reservaId = Long.parseLong(req.params("id"));
                    Map<String, Object> body = new Gson().fromJson(req.body(), Map.class);
                    Double valorPago = ((Number) body.get("valor")).doubleValue();
        
                    Reserva reserva = reservaServico.buscarPorId(reservaId);
        
                    if (reserva == null) {
                        res.status(404);
                        return "{\"error\": \"Reserva não encontrada\"}";
                    }
        
                    String resultado = reservaServico.processarPagamento(reserva, valorPago);
        
                    res.type("application/json");
                    return "{\"message\": \"" + resultado + "\", \"reservaId\": " + reservaId + "}";
        
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"Erro ao processar pagamento: " + e.getMessage() + "\"}";
                }
            }); 

            delete("/:id", (req, res) -> {
                Long id = Long.parseLong(req.params(":id"));
                reservaServico.remover(id);
                res.status(204);
                return "";
            });
        });
    }
}
