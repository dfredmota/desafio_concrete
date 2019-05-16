package concrete.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import concrete.model.Usuario;

/**
 * 
 * @author fred
 *
 */
@Repository
@Transactional
public class UsuarioDAO {
	
	@PersistenceContext
    protected EntityManager em; 
	
	public Usuario save(final Usuario entity) {
		Session session = (Session) em.unwrap(Session.class);
		session.persist(entity);
		session.flush();
		return entity;
	}
	
	public Usuario getUsuarioPorEmail(String email) {
		
		StringBuilder sql = new StringBuilder("from Usuario usuario  where usuario.email = :email ");
	     
		 javax.persistence.Query query = em.createQuery(sql.toString());

		 if(email != null && !email.trim().isEmpty()){
			 query.setParameter("email", email);
		 }
		 
		 List<Usuario> lista = query.getResultList();
		 
		 if(lista != null && !lista.isEmpty())
			 return lista.get(0);
		 else
			 return null;
	
	}
	

	public Usuario update(Usuario entity) {
		Session session = (Session) em.unwrap(Session.class);
		session.merge(entity);
		session.flush();
		return entity;
	}

	@Transactional
	public Usuario getById(final Long id) {
		return em.find(Usuario.class, id);
	}

}
