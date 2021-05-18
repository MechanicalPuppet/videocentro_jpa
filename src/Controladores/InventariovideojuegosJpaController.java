/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Inventariovideojuegos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Videojuegos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jbran
 */
public class InventariovideojuegosJpaController implements Serializable {

    public InventariovideojuegosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("videocentro_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventariovideojuegos inventariovideojuegos) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Videojuegos videojuegosOrphanCheck = inventariovideojuegos.getVideojuegos();
        if (videojuegosOrphanCheck != null) {
            Inventariovideojuegos oldInventariovideojuegosOfVideojuegos = videojuegosOrphanCheck.getInventariovideojuegos();
            if (oldInventariovideojuegosOfVideojuegos != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Videojuegos " + videojuegosOrphanCheck + " already has an item of type Inventariovideojuegos whose videojuegos column cannot be null. Please make another selection for the videojuegos field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuegos videojuegos = inventariovideojuegos.getVideojuegos();
            if (videojuegos != null) {
                videojuegos = em.getReference(videojuegos.getClass(), videojuegos.getNumCatalogo());
                inventariovideojuegos.setVideojuegos(videojuegos);
            }
            em.persist(inventariovideojuegos);
            if (videojuegos != null) {
                videojuegos.setInventariovideojuegos(inventariovideojuegos);
                videojuegos = em.merge(videojuegos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventariovideojuegos(inventariovideojuegos.getNumCatalogo()) != null) {
                throw new PreexistingEntityException("Inventariovideojuegos " + inventariovideojuegos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventariovideojuegos inventariovideojuegos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventariovideojuegos persistentInventariovideojuegos = em.find(Inventariovideojuegos.class, inventariovideojuegos.getNumCatalogo());
            Videojuegos videojuegosOld = persistentInventariovideojuegos.getVideojuegos();
            Videojuegos videojuegosNew = inventariovideojuegos.getVideojuegos();
            List<String> illegalOrphanMessages = null;
            if (videojuegosNew != null && !videojuegosNew.equals(videojuegosOld)) {
                Inventariovideojuegos oldInventariovideojuegosOfVideojuegos = videojuegosNew.getInventariovideojuegos();
                if (oldInventariovideojuegosOfVideojuegos != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Videojuegos " + videojuegosNew + " already has an item of type Inventariovideojuegos whose videojuegos column cannot be null. Please make another selection for the videojuegos field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (videojuegosNew != null) {
                videojuegosNew = em.getReference(videojuegosNew.getClass(), videojuegosNew.getNumCatalogo());
                inventariovideojuegos.setVideojuegos(videojuegosNew);
            }
            inventariovideojuegos = em.merge(inventariovideojuegos);
            if (videojuegosOld != null && !videojuegosOld.equals(videojuegosNew)) {
                videojuegosOld.setInventariovideojuegos(null);
                videojuegosOld = em.merge(videojuegosOld);
            }
            if (videojuegosNew != null && !videojuegosNew.equals(videojuegosOld)) {
                videojuegosNew.setInventariovideojuegos(inventariovideojuegos);
                videojuegosNew = em.merge(videojuegosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = inventariovideojuegos.getNumCatalogo();
                if (findInventariovideojuegos(id) == null) {
                    throw new NonexistentEntityException("The inventariovideojuegos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventariovideojuegos inventariovideojuegos;
            try {
                inventariovideojuegos = em.getReference(Inventariovideojuegos.class, id);
                inventariovideojuegos.getNumCatalogo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventariovideojuegos with id " + id + " no longer exists.", enfe);
            }
            Videojuegos videojuegos = inventariovideojuegos.getVideojuegos();
            if (videojuegos != null) {
                videojuegos.setInventariovideojuegos(null);
                videojuegos = em.merge(videojuegos);
            }
            em.remove(inventariovideojuegos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventariovideojuegos> findInventariovideojuegosEntities() {
        return findInventariovideojuegosEntities(true, -1, -1);
    }

    public List<Inventariovideojuegos> findInventariovideojuegosEntities(int maxResults, int firstResult) {
        return findInventariovideojuegosEntities(false, maxResults, firstResult);
    }

    private List<Inventariovideojuegos> findInventariovideojuegosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventariovideojuegos.class));
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

    public Inventariovideojuegos findInventariovideojuegos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventariovideojuegos.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventariovideojuegosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventariovideojuegos> rt = cq.from(Inventariovideojuegos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
