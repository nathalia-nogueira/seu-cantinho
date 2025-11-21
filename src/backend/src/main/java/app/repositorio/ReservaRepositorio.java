package app.repositorio;

import app.modelo.Filial;
import jakarta.persistence.*;
import java.util.List;

public class ReservaRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Reserva reserva) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(reserva);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Filial buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Reserva.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Reserva> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Reserva", Reserva.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Reserva reserva) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(reserva);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Reserva dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Reserva dadosExistentes = entityManager.find(Reserva.class, id);

            if (dadosExistentes != null) {
                if (dadosNovos.getDataHoraInicio() != null)
                    dadosExistentes.setDataHoraInicio(dadosNovos.getDataHoraInicio());
                
                if (dadosNovos.getDataHoraFim() != null)
                    dadosExistentes.setDataHoraFim(dadosNovos.getDataHoraFim());

                if (dadosNovos.getSinal() != 0)
                    dadosExistentes.setSinal(dadosNovos.getSinal());
                
                if (dadosNovos.getValorTotal() != 0)
                    dadosExistentes.setValorTotal(dadosNovos.getValorTotal());
                
                if (dadosNovos.getStatusReserva() != null)
                    dadosExistentes.setStatusReserva(dadosNovos.getStatusReserva());
                
                if (dadosNovos.getStatusPagamento() != null)
                    dadosExistentes.setStatusPagamento(dadosNovos.getStatusPagamento());
                
                if (dadosNovos.getCliente() != null)
                    dadosExistentes.setCliente(dadosNovos.getCliente());
                
                if (dadosNovos.getEspaco() != null)
                    dadosExistentes.setEspaco(dadosNovos.getEspaco());
                
                if (dadosNovos.getPagamentos() != null)
                    dadosExistentes.setPagamentos(dadosNovos.getPagamentos());
            }

            entityManager.merge(dadosExistentes);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void remover(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        
        try {
            entityManager.getTransaction().begin();
            Filial filial = entityManager.find(Filial.class, id);
            if (filial != null) {
                entityManager.remove(filial);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
