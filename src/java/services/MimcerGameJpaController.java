/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.MimcerGame;
import model.MimcerUsers;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author Ismael
 */
public class MimcerGameJpaController implements Serializable {

    public MimcerGameJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MimcerGame mimcerGame) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MimcerUsers idUser = mimcerGame.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                mimcerGame.setIdUser(idUser);
            }
            em.persist(mimcerGame);
            if (idUser != null) {
                idUser.getMimcerGameList().add(mimcerGame);
                idUser = em.merge(idUser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MimcerGame mimcerGame) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MimcerGame persistentMimcerGame = em.find(MimcerGame.class, mimcerGame.getIdGame());
            MimcerUsers idUserOld = persistentMimcerGame.getIdUser();
            MimcerUsers idUserNew = mimcerGame.getIdUser();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                mimcerGame.setIdUser(idUserNew);
            }
            mimcerGame = em.merge(mimcerGame);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getMimcerGameList().remove(mimcerGame);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getMimcerGameList().add(mimcerGame);
                idUserNew = em.merge(idUserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mimcerGame.getIdGame();
                if (findMimcerGame(id) == null) {
                    throw new NonexistentEntityException("The mimcerGame with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MimcerGame mimcerGame;
            try {
                mimcerGame = em.getReference(MimcerGame.class, id);
                mimcerGame.getIdGame();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mimcerGame with id " + id + " no longer exists.", enfe);
            }
            MimcerUsers idUser = mimcerGame.getIdUser();
            if (idUser != null) {
                idUser.getMimcerGameList().remove(mimcerGame);
                idUser = em.merge(idUser);
            }
            em.remove(mimcerGame);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MimcerGame> findMimcerGameEntities() {
        return findMimcerGameEntities(true, -1, -1);
    }

    public List<MimcerGame> findMimcerGameEntities(int maxResults, int firstResult) {
        return findMimcerGameEntities(false, maxResults, firstResult);
    }

    private List<MimcerGame> findMimcerGameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MimcerGame.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MimcerGame findMimcerGame(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MimcerGame.class, id);
        } finally {
            em.close();
        }
    }

    public int getMimcerGameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MimcerGame> rt = cq.from(MimcerGame.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
