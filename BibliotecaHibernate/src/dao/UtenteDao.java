package dao;

import java.util.ArrayList;
import java.util.List;

import hibernateUtil.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.*;

public class UtenteDao {
	
public boolean creaUtente(Utente u) {
	boolean result=false;

	Session session =HibernateUtil.openSession();
	Transaction tx=null;

	try{
		tx=session.getTransaction();
		tx.begin();
		
		session.persist(u);
		result=true;
		
		tx.commit();
	}catch(Exception ex){
		tx.rollback();
	}finally{
		session.close();
	}
	
	return result;
}

public Utente trovaUtente(String codiceFiscale)
{
	Utente u=null;
	Session session =HibernateUtil.openSession();
	Transaction tx=null;

	try{
		tx=session.getTransaction();
		tx.begin();


		Query query= session.createQuery("from Utente where codiceFiscale=:x");
		query.setString("x", codiceFiscale);
		u=(Utente) query.uniqueResult();
		
		tx.commit();
	}catch(Exception ex){
		tx.rollback();
	}finally{
		session.close();
	}
	return u;
}

@SuppressWarnings("unchecked")
public List<Utente> getAllUtenti() {
	
	List<Utente> utenti= new ArrayList<Utente>();
	Session session =HibernateUtil.openSession();
	Transaction tx=null;

	try{
	tx=session.getTransaction();
	tx.begin();

	Query query= session.createQuery("from Utente");
	utenti=query.list();
	
	 tx.commit();
	}catch(Exception ex){
		tx.rollback();
	}finally{
		session.close();
	}
return utenti;
}

public boolean aggiornaUtente(Utente u) {
	boolean result=false;
	Session session =HibernateUtil.openSession();
	Transaction tx=null;

	try{
		tx=session.getTransaction();
		tx.begin();

		session.update(u);

		result=true;
		tx.commit();
	}catch(Exception ex){
		tx.rollback();
	}finally{
		session.close();
	}
	return result;

}

}
