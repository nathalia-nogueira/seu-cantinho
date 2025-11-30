package app.repositorio;

import app.modelo.*;
import jakarta.persistence.*;
import java.util.List;

public class ClienteRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Cliente cliente) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Cliente buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Cliente.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Cliente> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE TYPE(u) = Cliente", Cliente.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Cliente cliente) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Cliente dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cliente dadosExistentes = entityManager.find(Cliente.class, id);
            if (dadosExistentes != null) {
                if (dadosNovos.getNome() != null)
                    dadosExistentes.setNome(dadosNovos.getNome());
                
                if (dadosNovos.getCpf() != null)
                    dadosExistentes.setCpf(dadosNovos.getCpf());
                
                if (dadosNovos.getEmail() != null)
                    dadosExistentes.setEmail(dadosNovos.getEmail());

                if (dadosNovos.getTelefone() != null)
                    dadosExistentes.setTelefone(dadosNovos.getTelefone());
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
            Cliente cliente = entityManager.find(Cliente.class, id);
            if (cliente != null) {
                entityManager.remove(cliente);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}