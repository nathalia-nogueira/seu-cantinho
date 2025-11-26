package app.repositorio;

import app.modelo.*;
import jakarta.persistence.*;
import java.util.List;

public class EnderecoRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Endereco endereco) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(endereco);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Endereco buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Endereco.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Endereco> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Endereco", Endereco.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Endereco endereco) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(endereco);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Endereco dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Endereco dadosExistentes = entityManager.find(Endereco.class, id);
            if (dadosExistentes != null) {
                if (dadosNovos.getEstado() != null)
                    dadosExistentes.setEstado(dadosNovos.getEstado());
                
                if (dadosNovos.getCidade() != null)
                    dadosExistentes.setCidade(dadosNovos.getCidade());
                
                if (dadosNovos.getRua() != null)
                    dadosExistentes.setRua(dadosNovos.getRua());

                if (dadosNovos.getNumero() != null)
                    dadosExistentes.setNumero(dadosNovos.getNumero());
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
            Endereco endereco = entityManager.find(Endereco.class, id);
            if (endereco != null) {
                entityManager.remove(endereco);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
