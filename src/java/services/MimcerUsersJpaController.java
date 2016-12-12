/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.MimcerGame;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.MimcerUsers;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author Ismael
 */
public class MimcerUsersJpaController implements Serializable {

    public MimcerUsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MimcerUsers mimcerUsers) {
        if (mimcerUsers.getMimcerGameList() == null) {
            mimcerUsers.setMimcerGameList(new ArrayList<MimcerGame>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MimcerGame> attachedMimcerGameList = new ArrayList<MimcerGame>();
            for (MimcerGame mimcerGameListMimcerGameToAttach : mimcerUsers.getMimcerGameList()) {
                mimcerGameListMimcerGameToAttach = em.getReference(mimcerGameListMimcerGameToAttach.getClass(), mimcerGameListMimcerGameToAttach.getIdGame());
                attachedMimcerGameList.add(mimcerGameListMimcerGameToAttach);
            }
            mimcerUsers.setMimcerGameList(attachedMimcerGameList);
            em.persist(mimcerUsers);
            for (MimcerGame mimcerGameListMimcerGame : mimcerUsers.getMimcerGameList()) {
                MimcerUsers oldIdUserOfMimcerGameListMimcerGame = mimcerGameListMimcerGame.getIdUser();
                mimcerGameListMimcerGame.setIdUser(mimcerUsers);
                mimcerGameListMimcerGame = em.merge(mimcerGameListMimcerGame);
                if (oldIdUserOfMimcerGameListMimcerGame != null) {
                    oldIdUserOfMimcerGameListMimcerGame.getMimcerGameList().remove(mimcerGameListMimcerGame);
                    oldIdUserOfMimcerGameListMimcerGame = em.merge(oldIdUserOfMimcerGameListMimcerGame);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MimcerUsers mimcerUsers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MimcerUsers persistentMimcerUsers = em.find(MimcerUsers.class, mimcerUsers.getIdUser());
            List<MimcerGame> mimcerGameListOld = persistentMimcerUsers.getMimcerGameList();
            List<MimcerGame> mimcerGameListNew = mimcerUsers.getMimcerGameList();
            List<MimcerGame> attachedMimcerGameListNew = new ArrayList<MimcerGame>();
            for (MimcerGame mimcerGameListNewMimcerGameToAttach : mimcerGameListNew) {
                mimcerGameListNewMimcerGameToAttach = em.getReference(mimcerGameListNewMimcerGameToAttach.getClass(), mimcerGameListNewMimcerGameToAttach.getIdGame());
                attachedMimcerGameListNew.add(mimcerGameListNewMimcerGameToAttach);
            }
            mimcerGameListNew = attachedMimcerGameListNew;
            mimcerUsers.setMimcerGameList(mimcerGameListNew);
            mimcerUsers = em.merge(mimcerUsers);
            for (MimcerGame mimcerGameListOldMimcerGame : mimcerGameListOld) {
                if (!mimcerGameListNew.contains(mimcerGameListOldMimcerGame)) {
                    mimcerGameListOldMimcerGame.setIdUser(null);
                    mimcerGameListOldMimcerGame = em.merge(mimcerGameListOldMimcerGame);
                }
            }
            for (MimcerGame mimcerGameListNewMimcerGame : mimcerGameListNew) {
                if (!mimcerGameListOld.contains(mimcerGameListNewMimcerGame)) {
                    MimcerUsers oldIdUserOfMimcerGameListNewMimcerGame = mimcerGameListNewMimcerGame.getIdUser();
                    mimcerGameListNewMimcerGame.setIdUser(mimcerUsers);
                    mimcerGameListNewMimcerGame = em.merge(mimcerGameListNewMimcerGame);
                    if (oldIdUserOfMimcerGameListNewMimcerGame != null && !oldIdUserOfMimcerGameListNewMimcerGame.equals(mimcerUsers)) {
                        oldIdUserOfMimcerGameListNewMimcerGame.getMimcerGameList().remove(mimcerGameListNewMimcerGame);
                        oldIdUserOfMimcerGameListNewMimcerGame = em.merge(oldIdUserOfMimcerGameListNewMimcerGame);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mimcerUsers.getIdUser();
                if (findMimcerUsers(id) == null) {
                    throw new NonexistentEntityException("The mimcerUsers with id " + id + " no longer exists.");
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
            MimcerUsers mimcerUsers;
            try {
                mimcerUsers = em.getReference(MimcerUsers.class, id);
                mimcerUsers.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mimcerUsers with id " + id + " no longer exists.", enfe);
            }
            List<MimcerGame> mimcerGameList = mimcerUsers.getMimcerGameList();
            for (MimcerGame mimcerGameListMimcerGame : mimcerGameList) {
                mimcerGameListMimcerGame.setIdUser(null);
                mimcerGameListMimcerGame = em.merge(mimcerGameListMimcerGame);
            }
            em.remove(mimcerUsers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MimcerUsers> findMimcerUsersEntities() {
        return findMimcerUsersEntities(true, -1, -1);
    }

    public List<MimcerUsers> findMimcerUsersEntities(int maxResults, int firstResult) {
        return findMimcerUsersEntities(false, maxResults, firstResult);
    }

    private List<MimcerUsers> findMimcerUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MimcerUsers.class));
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

    public MimcerUsers findMimcerUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MimcerUsers.class, id);
        } finally {
            em.close();
        }
    }

    public int getMimcerUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MimcerUsers> rt = cq.from(MimcerUsers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
