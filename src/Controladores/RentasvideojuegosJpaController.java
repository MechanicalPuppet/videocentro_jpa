/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Clientes;
import Entidades.Rentasvideojuegos;
import Entidades.RentasvideojuegosPK;
import Entidades.Videojuegos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jbran
 */
public class RentasvideojuegosJpaController implements Serializable {

    public RentasvideojuegosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("videocentro_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rentasvideojuegos rentasvideojuegos) throws PreexistingEntityException, Exception {
        if (rentasvideojuegos.getRentasvideojuegosPK() == null) {
            rentasvideojuegos.setRentasvideojuegosPK(new RentasvideojuegosPK());
        }
        rentasvideojuegos.getRentasvideojuegosPK().setNumCredencial(rentasvideojuegos.getClientes().getNumCredencial());
        rentasvideojuegos.getRentasvideojuegosPK().setNumCatalogo(rentasvideojuegos.getVideojuegos().getNumCatalogo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes = rentasvideojuegos.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getNumCredencial());
                rentasvideojuegos.setClientes(clientes);
            }
            Videojuegos videojuegos = rentasvideojuegos.getVideojuegos();
            if (videojuegos != null) {
                videojuegos = em.getReference(videojuegos.getClass(), videojuegos.getNumCatalogo());
                rentasvideojuegos.setVideojuegos(videojuegos);
            }
            em.persist(rentasvideojuegos);
            if (clientes != null) {
                clientes.getRentasvideojuegosList().add(rentasvideojuegos);
                clientes = em.merge(clientes);
            }
            if (videojuegos != null) {
                videojuegos.getRentasvideojuegosList().add(rentasvideojuegos);
                videojuegos = em.merge(videojuegos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRentasvideojuegos(rentasvideojuegos.getRentasvideojuegosPK()) != null) {
                throw new PreexistingEntityException("Rentasvideojuegos " + rentasvideojuegos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rentasvideojuegos rentasvideojuegos) throws NonexistentEntityException, Exception {
        rentasvideojuegos.getRentasvideojuegosPK().setNumCredencial(rentasvideojuegos.getClientes().getNumCredencial());
        rentasvideojuegos.getRentasvideojuegosPK().setNumCatalogo(rentasvideojuegos.getVideojuegos().getNumCatalogo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rentasvideojuegos persistentRentasvideojuegos = em.find(Rentasvideojuegos.class, rentasvideojuegos.getRentasvideojuegosPK());
            Clientes clientesOld = persistentRentasvideojuegos.getClientes();
            Clientes clientesNew = rentasvideojuegos.getClientes();
            Videojuegos videojuegosOld = persistentRentasvideojuegos.getVideojuegos();
            Videojuegos videojuegosNew = rentasvideojuegos.getVideojuegos();
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getNumCredencial());
                rentasvideojuegos.setClientes(clientesNew);
            }
            if (videojuegosNew != null) {
                videojuegosNew = em.getReference(videojuegosNew.getClass(), videojuegosNew.getNumCatalogo());
                rentasvideojuegos.setVideojuegos(videojuegosNew);
            }
            rentasvideojuegos = em.merge(rentasvideojuegos);
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                clientesOld.getRentasvideojuegosList().remove(rentasvideojuegos);
                clientesOld = em.merge(clientesOld);
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.getRentasvideojuegosList().add(rentasvideojuegos);
                clientesNew = em.merge(clientesNew);
            }
            if (videojuegosOld != null && !videojuegosOld.equals(videojuegosNew)) {
                videojuegosOld.getRentasvideojuegosList().remove(rentasvideojuegos);
                videojuegosOld = em.merge(videojuegosOld);
            }
            if (videojuegosNew != null && !videojuegosNew.equals(videojuegosOld)) {
                videojuegosNew.getRentasvideojuegosList().add(rentasvideojuegos);
                videojuegosNew = em.merge(videojuegosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RentasvideojuegosPK id = rentasvideojuegos.getRentasvideojuegosPK();
                if (findRentasvideojuegos(id) == null) {
                    throw new NonexistentEntityException("The rentasvideojuegos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RentasvideojuegosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rentasvideojuegos rentasvideojuegos;
            try {
                rentasvideojuegos = em.getReference(Rentasvideojuegos.class, id);
                rentasvideojuegos.getRentasvideojuegosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rentasvideojuegos with id " + id + " no longer exists.", enfe);
            }
            Clientes clientes = rentasvideojuegos.getClientes();
            if (clientes != null) {
                clientes.getRentasvideojuegosList().remove(rentasvideojuegos);
                clientes = em.merge(clientes);
            }
            Videojuegos videojuegos = rentasvideojuegos.getVideojuegos();
            if (videojuegos != null) {
                videojuegos.getRentasvideojuegosList().remove(rentasvideojuegos);
                videojuegos = em.merge(videojuegos);
            }
            em.remove(rentasvideojuegos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rentasvideojuegos> findRentasvideojuegosEntities() {
        return findRentasvideojuegosEntities(true, -1, -1);
    }

    public List<Rentasvideojuegos> findRentasvideojuegosEntities(int maxResults, int firstResult) {
        return findRentasvideojuegosEntities(false, maxResults, firstResult);
    }

    private List<Rentasvideojuegos> findRentasvideojuegosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rentasvideojuegos.class));
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

    public Rentasvideojuegos findRentasvideojuegos(RentasvideojuegosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rentasvideojuegos.class, id);
        } finally {
            em.close();
        }
    }

    public int getRentasvideojuegosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rentasvideojuegos> rt = cq.from(Rentasvideojuegos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
