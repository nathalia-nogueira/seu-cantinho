package app.repositorio;

import app.modelo.Usuario;
import jakarta.persistence.*;
import java.util.List;

public class UsuarioRepositorio {

    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("seuCantinhoPU");

    // CREATE
    public void salvar(Usuario usuario) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // READ
    public Usuario buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Usuario.class, id);
        } finally {
            entityManager.close();
        }
    }

    // LIST
    public List<Usuario> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Usuario", Usuario.class)
                                .getResultList();
        } finally {
            entityManager.close();
        }
    }

    // UPDATE COMPLETO
    public void atualizar(Usuario usuario) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // UPDATE PARCIAL
    public void atualizarParcial(Long id, Usuario dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Usuario existente = entityManager.find(Usuario.class, id);
            if (existente != null) {
                if (dadosNovos.getNome() != null)
                    existente.setNome(dadosNovos.getNome());
                if (dadosNovos.getCpf() != null)
                    existente.setCpf(dadosNovos.getCpf());
                if (dadosNovos.getEmail() != null)
                    existente.setEmail(dadosNovos.getEmail());
                if (dadosNovos.getTelefone() != null)
                    existente.setTelefone(dadosNovos.getTelefone());
            }
            entityManager.merge(existente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // DELETE
    public void remover(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
