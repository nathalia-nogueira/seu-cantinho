package app.repositorio;

import app.modelo.Filial;
import jakarta.persistence.*;
import java.util.List;

public class PagamentoRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Pagamento pagamento) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pagamento);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Pagamento buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Pagamento.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Pagamento> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Pagamento", Pagamento.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Pagamento pagamento) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pagamento);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Pagamento dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Pagamento dadosExistentes = entityManager.find(Pagamento.class, id);
            
            if (dadosExistentes != null) {
                if (dadosNovos.getValor() != 0)
                    dadosExistentes.setValor(dadosNovos.getValor());

                if (dadosNovos.getData() != null)
                    dadosExistentes.setData(dadosNovos.getData());
                
                if (dadosNovos.getReserva() != null)
                    dadosExistentes.setReserva(dadosNovos.getReserva());
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
