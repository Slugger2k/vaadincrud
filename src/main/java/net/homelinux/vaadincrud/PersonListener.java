/**
 * @author Christian Mueller (christian.muell3r@gmail.com)
 * Date: 04-2018
 */
package net.homelinux.vaadincrud;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.cfg.Configuration;
import org.vaadin.crudui.crud.CrudListener;

public class PersonListener implements CrudListener<Person> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3362857741030959671L;
	private EntityManager em;
	
	public PersonListener() {
		em = new Configuration().configure().buildSessionFactory().createEntityManager();
	}

	@Override
	public Collection<Person> findAll() {
		try {
			em.getTransaction().begin();
			TypedQuery<Person> typedQuery = em.createQuery("from Person", Person.class);
			List<Person> resultList = typedQuery.getResultList();
			em.getTransaction().commit();			
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return null;
		}

	}

	@Override
	public Person add(Person domainObjectToAdd) {
		try {
			em.getTransaction().begin();
			em.persist(domainObjectToAdd);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		return domainObjectToAdd;
	}

	@Override
	public Person update(Person domainObjectToUpdate) {
		try {
			em.getTransaction().begin();
			em.flush();
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		return domainObjectToUpdate;
	}

	@Override
	public void delete(Person domainObjectToDelete) {
		try {
			em.getTransaction().begin();
			em.remove(domainObjectToDelete);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

}
