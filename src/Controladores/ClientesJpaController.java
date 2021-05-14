/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Rentasvideojuegos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jbran
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("videocentro_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getRentasvideojuegosList() == null) {
            clientes.setRentasvideojuegosList(new ArrayList<Rentasvideojuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Rentasvideojuegos> attachedRentasvideojuegosList = new ArrayList<Rentasvideojuegos>();
            for (Rentasvideojuegos rentasvideojuegosListRentasvideojuegosToAttach : clientes.getRentasvideojuegosList()) {
                rentasvideojuegosListRentasvideojuegosToAttach = em.getReference(rentasvideojuegosListRentasvideojuegosToAttach.getClass(), rentasvideojuegosListRentasvideojuegosToAttach.getRentasvideojuegosPK());
                attachedRentasvideojuegosList.add(rentasvideojuegosListRentasvideojuegosToAttach);
            }
            clientes.setRentasvideojuegosList(attachedRentasvideojuegosList);
            em.persist(clientes);
            for (Rentasvideojuegos rentasvideojuegosListRentasvideojuegos : clientes.getRentasvideojuegosList()) {
                Clientes oldClientesOfRentasvideojuegosListRentasvideojuegos = rentasvideojuegosListRentasvideojuegos.getClientes();
                rentasvideojuegosListRentasvideojuegos.setClientes(clientes);
                rentasvideojuegosListRentasvideojuegos = em.merge(rentasvideojuegosListRentasvideojuegos);
                if (oldClientesOfRentasvideojuegosListRentasvideojuegos != null) {
                    oldClientesOfRentasvideojuegosListRentasvideojuegos.getRentasvideojuegosList().remove(rentasvideojuegosListRentasvideojuegos);
                    oldClientesOfRentasvideojuegosListRentasvideojuegos = em.merge(oldClientesOfRentasvideojuegosListRentasvideojuegos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getNumCredencial()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getNumCredencial());
            List<Rentasvideojuegos> rentasvideojuegosListOld = persistentClientes.getRentasvideojuegosList();
            List<Rentasvideojuegos> rentasvideojuegosListNew = clientes.getRentasvideojuegosList();
            List<String> illegalOrphanMessages = null;
            for (Rentasvideojuegos rentasvideojuegosListOldRentasvideojuegos : rentasvideojuegosListOld) {
                if (!rentasvideojuegosListNew.contains(rentasvideojuegosListOldRentasvideojuegos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rentasvideojuegos " + rentasvideojuegosListOldRentasvideojuegos + " since its clientes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Rentasvideojuegos> attachedRentasvideojuegosListNew = new ArrayList<Rentasvideojuegos>();
            for (Rentasvideojuegos rentasvideojuegosListNewRentasvideojuegosToAttach : rentasvideojuegosListNew) {
                rentasvideojuegosListNewRentasvideojuegosToAttach = em.getReference(rentasvideojuegosListNewRentasvideojuegosToAttach.getClass(), rentasvideojuegosListNewRentasvideojuegosToAttach.getRentasvideojuegosPK());
                attachedRentasvideojuegosListNew.add(rentasvideojuegosListNewRentasvideojuegosToAttach);
            }
            rentasvideojuegosListNew = attachedRentasvideojuegosListNew;
            clientes.setRentasvideojuegosList(rentasvideojuegosListNew);
            clientes = em.merge(clientes);
            for (Rentasvideojuegos rentasvideojuegosListNewRentasvideojuegos : rentasvideojuegosListNew) {
                if (!rentasvideojuegosListOld.contains(rentasvideojuegosListNewRentasvideojuegos)) {
                    Clientes oldClientesOfRentasvideojuegosListNewRentasvideojuegos = rentasvideojuegosListNewRentasvideojuegos.getClientes();
                    rentasvideojuegosListNewRentasvideojuegos.setClientes(clientes);
                    rentasvideojuegosListNewRentasvideojuegos = em.merge(rentasvideojuegosListNewRentasvideojuegos);
                    if (oldClientesOfRentasvideojuegosListNewRentasvideojuegos != null && !oldClientesOfRentasvideojuegosListNewRentasvideojuegos.equals(clientes)) {
                        oldClientesOfRentasvideojuegosListNewRentasvideojuegos.getRentasvideojuegosList().remove(rentasvideojuegosListNewRentasvideojuegos);
                        oldClientesOfRentasvideojuegosListNewRentasvideojuegos = em.merge(oldClientesOfRentasvideojuegosListNewRentasvideojuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clientes.getNumCredencial();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getNumCredencial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rentasvideojuegos> rentasvideojuegosListOrphanCheck = clientes.getRentasvideojuegosList();
            for (Rentasvideojuegos rentasvideojuegosListOrphanCheckRentasvideojuegos : rentasvideojuegosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Rentasvideojuegos " + rentasvideojuegosListOrphanCheckRentasvideojuegos + " in its rentasvideojuegosList field has a non-nullable clientes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
