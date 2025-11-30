package app.repositorio;

import app.modelo.*;
import jakarta.persistence.*;
import java.util.List;

public class AdministradorRepositorio {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("seuCantinhoPU");

    public void salvar(Administrador administrador) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(administrador);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Administrador buscarPorId(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Administrador.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Administrador> listar() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE TYPE(u) = Administrador", Administrador.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void atualizar(Administrador administrador) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(administrador);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void atualizarParcialmente(Long id, Administrador dadosNovos) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Administrador dadosExistentes = entityManager.find(Administrador.class, id);
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
            Administrador administrador = entityManager.find(Administrador.class, id);
            if (administrador != null) {
                entityManager.remove(administrador);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Administrador> listarPorTipo(Class<? extends Administrador> tipo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Administrador u WHERE TYPE(u) = :tipo", Administrador.class)
            .setParameter("tipo", tipo).getResultList();
        } finally {
            entityManager.close();
        }
    }
}