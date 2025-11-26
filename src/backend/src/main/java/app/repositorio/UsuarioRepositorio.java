package app.repositorio;

import app.modelo.*;
import jakarta.persistence.*;
import java.util.List;

public class UsuarioRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

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

    public Usuario buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Usuario.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Usuario> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

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

    public void atualizarParcialmente(Long id, Usuario dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Usuario dadosExistentes = entityManager.find(Usuario.class, id);
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
