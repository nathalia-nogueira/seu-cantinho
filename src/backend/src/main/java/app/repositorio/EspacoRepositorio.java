package app.repositorio;

import app.modelo.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class EspacoRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Espaco espaco) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(espaco);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Espaco buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Espaco.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Espaco> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Espaco", Espaco.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Espaco espaco) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(espaco);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Espaco dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Espaco dadosExistentes = entityManager.find(Espaco.class, id);
            if (dadosExistentes != null) {
                if (dadosNovos.getNome() != null)
                    dadosExistentes.setNome(dadosNovos.getNome());
                
                if (dadosNovos.getDescricao() != null)
                    dadosExistentes.setDescricao(dadosNovos.getDescricao());
                
                if (dadosNovos.getFotos() != null)
                    dadosExistentes.setFotos(dadosNovos.getFotos());

                if (dadosNovos.getCapacidade() != null)
                    dadosExistentes.setCapacidade(dadosNovos.getCapacidade());

                if (dadosNovos.getPrecoDiaria() != 0) 
                    dadosExistentes.setPrecoDiaria(dadosNovos.getPrecoDiaria());

                if (dadosNovos.getEndereco() != null)
                    dadosExistentes.setEndereco(dadosNovos.getEndereco());

                if (dadosNovos.getFilial() != null)
                    dadosExistentes.setFilial(dadosNovos.getFilial());
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
            Espaco espaco = entityManager.find(Espaco.class, id);
            if (espaco != null) {
                entityManager.remove(espaco);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Espaco> buscarDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("""
                SELECT e FROM Espaco e
                WHERE e.id NOT IN (
                    SELECT r.espaco.id FROM Reserva r 
                    WHERE (r.dataHoraInicio <= :fim AND r.dataHoraFim >= :inicio)
                )
            """, Espaco.class).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
