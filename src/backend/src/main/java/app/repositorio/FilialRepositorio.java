package app.repositorio;

import app.modelo.Filial;
import jakarta.persistence.*;
import java.util.List;

public class FilialRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Filial filial) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(filial);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Filial buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Filial.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Filial> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Filial", Filial.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Filial filial) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(filial);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Filial dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Filial dadosExistentes = entityManager.find(Filial.class, id);
            
            if (dadosExistentes != null) {
                if (dadosNovos.getNome() != null)
                    dadosExistentes.setNome(dadosNovos.getNome());

                if (dadosNovos.getEndereco() != null)
                    dadosExistentes.setEndereco(dadosNovos.getEndereco());
                
                if (dadosNovos.getEspacos() != null)
                    dadosExistentes.setEspacos(dadosNovos.getEspacos());
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
