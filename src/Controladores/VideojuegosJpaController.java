/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Inventariovideojuegos;
import Entidades.Rentasvideojuegos;
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
public class VideojuegosJpaController implements Serializable {

    public VideojuegosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("videocentro_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Videojuegos videojuegos) throws PreexistingEntityException, Exception {
        if (videojuegos.getRentasvideojuegosList() == null) {
            videojuegos.setRentasvideojuegosList(new ArrayList<Rentasvideojuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventariovideojuegos inventariovideojuegos = videojuegos.getInventariovideojuegos();
            if (inventariovideojuegos != null) {
                inventariovideojuegos = em.getReference(inventariovideojuegos.getClass(), inventariovideojuegos.getNumCatalogo());
                videojuegos.setInventariovideojuegos(inventariovideojuegos);
            }
            List<Rentasvideojuegos> attachedRentasvideojuegosList = new ArrayList<Rentasvideojuegos>();
            for (Rentasvideojuegos rentasvideojuegosListRentasvideojuegosToAttach : videojuegos.getRentasvideojuegosList()) {
                rentasvideojuegosListRentasvideojuegosToAttach = em.getReference(rentasvideojuegosListRentasvideojuegosToAttach.getClass(), rentasvideojuegosListRentasvideojuegosToAttach.getRentasvideojuegosPK());
                attachedRentasvideojuegosList.add(rentasvideojuegosListRentasvideojuegosToAttach);
            }
            videojuegos.setRentasvideojuegosList(attachedRentasvideojuegosList);
            em.persist(videojuegos);
            if (inventariovideojuegos != null) {
                Videojuegos oldVideojuegosOfInventariovideojuegos = inventariovideojuegos.getVideojuegos();
                if (oldVideojuegosOfInventariovideojuegos != null) {
                    oldVideojuegosOfInventariovideojuegos.setInventariovideojuegos(null);
                    oldVideojuegosOfInventariovideojuegos = em.merge(oldVideojuegosOfInventariovideojuegos);
                }
                inventariovideojuegos.setVideojuegos(videojuegos);
                inventariovideojuegos = em.merge(inventariovideojuegos);
            }
            for (Rentasvideojuegos rentasvideojuegosListRentasvideojuegos : videojuegos.getRentasvideojuegosList()) {
                Videojuegos oldVideojuegosOfRentasvideojuegosListRentasvideojuegos = rentasvideojuegosListRentasvideojuegos.getVideojuegos();
                rentasvideojuegosListRentasvideojuegos.setVideojuegos(videojuegos);
                rentasvideojuegosListRentasvideojuegos = em.merge(rentasvideojuegosListRentasvideojuegos);
                if (oldVideojuegosOfRentasvideojuegosListRentasvideojuegos != null) {
                    oldVideojuegosOfRentasvideojuegosListRentasvideojuegos.getRentasvideojuegosList().remove(rentasvideojuegosListRentasvideojuegos);
                    oldVideojuegosOfRentasvideojuegosListRentasvideojuegos = em.merge(oldVideojuegosOfRentasvideojuegosListRentasvideojuegos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVideojuegos(videojuegos.getNumCatalogo()) != null) {
                throw new PreexistingEntityException("Videojuegos " + videojuegos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Videojuegos videojuegos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuegos persistentVideojuegos = em.find(Videojuegos.class, videojuegos.getNumCatalogo());
            Inventariovideojuegos inventariovideojuegosOld = persistentVideojuegos.getInventariovideojuegos();
            Inventariovideojuegos inventariovideojuegosNew = videojuegos.getInventariovideojuegos();
            List<Rentasvideojuegos> rentasvideojuegosListOld = persistentVideojuegos.getRentasvideojuegosList();
            List<Rentasvideojuegos> rentasvideojuegosListNew = videojuegos.getRentasvideojuegosList();
            List<String> illegalOrphanMessages = null;
            if (inventariovideojuegosOld != null && !inventariovideojuegosOld.equals(inventariovideojuegosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Inventariovideojuegos " + inventariovideojuegosOld + " since its videojuegos field is not nullable.");
            }
            for (Rentasvideojuegos rentasvideojuegosListOldRentasvideojuegos : rentasvideojuegosListOld) {
                if (!rentasvideojuegosListNew.contains(rentasvideojuegosListOldRentasvideojuegos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rentasvideojuegos " + rentasvideojuegosListOldRentasvideojuegos + " since its videojuegos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (inventariovideojuegosNew != null) {
                inventariovideojuegosNew = em.getReference(inventariovideojuegosNew.getClass(), inventariovideojuegosNew.getNumCatalogo());
                videojuegos.setInventariovideojuegos(inventariovideojuegosNew);
            }
            List<Rentasvideojuegos> attachedRentasvideojuegosListNew = new ArrayList<Rentasvideojuegos>();
            for (Rentasvideojuegos rentasvideojuegosListNewRentasvideojuegosToAttach : rentasvideojuegosListNew) {
                rentasvideojuegosListNewRentasvideojuegosToAttach = em.getReference(rentasvideojuegosListNewRentasvideojuegosToAttach.getClass(), rentasvideojuegosListNewRentasvideojuegosToAttach.getRentasvideojuegosPK());
                attachedRentasvideojuegosListNew.add(rentasvideojuegosListNewRentasvideojuegosToAttach);
            }
            rentasvideojuegosListNew = attachedRentasvideojuegosListNew;
            videojuegos.setRentasvideojuegosList(rentasvideojuegosListNew);
            videojuegos = em.merge(videojuegos);
            if (inventariovideojuegosNew != null && !inventariovideojuegosNew.equals(inventariovideojuegosOld)) {
                Videojuegos oldVideojuegosOfInventariovideojuegos = inventariovideojuegosNew.getVideojuegos();
                if (oldVideojuegosOfInventariovideojuegos != null) {
                    oldVideojuegosOfInventariovideojuegos.setInventariovideojuegos(null);
                    oldVideojuegosOfInventariovideojuegos = em.merge(oldVideojuegosOfInventariovideojuegos);
                }
                inventariovideojuegosNew.setVideojuegos(videojuegos);
                inventariovideojuegosNew = em.merge(inventariovideojuegosNew);
            }
            for (Rentasvideojuegos rentasvideojuegosListNewRentasvideojuegos : rentasvideojuegosListNew) {
                if (!rentasvideojuegosListOld.contains(rentasvideojuegosListNewRentasvideojuegos)) {
                    Videojuegos oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos = rentasvideojuegosListNewRentasvideojuegos.getVideojuegos();
                    rentasvideojuegosListNewRentasvideojuegos.setVideojuegos(videojuegos);
                    rentasvideojuegosListNewRentasvideojuegos = em.merge(rentasvideojuegosListNewRentasvideojuegos);
                    if (oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos != null && !oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos.equals(videojuegos)) {
                        oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos.getRentasvideojuegosList().remove(rentasvideojuegosListNewRentasvideojuegos);
                        oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos = em.merge(oldVideojuegosOfRentasvideojuegosListNewRentasvideojuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = videojuegos.getNumCatalogo();
                if (findVideojuegos(id) == null) {
                    throw new NonexistentEntityException("The videojuegos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuegos videojuegos;
            try {
                videojuegos = em.getReference(Videojuegos.class, id);
                videojuegos.getNumCatalogo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The videojuegos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Inventariovideojuegos inventariovideojuegosOrphanCheck = videojuegos.getInventariovideojuegos();
            if (inventariovideojuegosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Videojuegos (" + videojuegos + ") cannot be destroyed since the Inventariovideojuegos " + inventariovideojuegosOrphanCheck + " in its inventariovideojuegos field has a non-nullable videojuegos field.");
            }
            List<Rentasvideojuegos> rentasvideojuegosListOrphanCheck = videojuegos.getRentasvideojuegosList();
            for (Rentasvideojuegos rentasvideojuegosListOrphanCheckRentasvideojuegos : rentasvideojuegosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Videojuegos (" + videojuegos + ") cannot be destroyed since the Rentasvideojuegos " + rentasvideojuegosListOrphanCheckRentasvideojuegos + " in its rentasvideojuegosList field has a non-nullable videojuegos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(videojuegos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Videojuegos> findVideojuegosEntities() {
        return findVideojuegosEntities(true, -1, -1);
    }

    public List<Videojuegos> findVideojuegosEntities(int maxResults, int firstResult) {
        return findVideojuegosEntities(false, maxResults, firstResult);
    }

    private List<Videojuegos> findVideojuegosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Videojuegos.class));
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

    public Videojuegos findVideojuegos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Videojuegos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideojuegosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Videojuegos> rt = cq.from(Videojuegos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
