package dao;

import java.util.ArrayList;
import java.util.List;

import hibernateUtil.HibernateUtil;
import model.Biblioteca;
import model.Libro;
import model.Prestito;
import model.Utente;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PrestitoDao {
	
	public boolean creaPrestito(Prestito p) {
		boolean result=false;

		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			
			session.persist(p);
			result=true;
			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		
		return result;
	}
	
	public List<Prestito> trovaPrestitiUtente(Biblioteca b, Utente u)
	{
		Prestito p=null;
		long idb=b.getId_Biblioteca();
		long idu=u.getId_Utente();
		List<Prestito> prestitiUtente=new ArrayList<Prestito>();
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			
			Query query=session.createQuery("from Prestito where Biblio_id_Biblioteca=:x1 and Utente_id_utente=:x2 order by data");
			query.setLong("x1", idb);
			query.setLong("x2", idu);
		
			prestitiUtente= query.list();

			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		return prestitiUtente;
	}
	
	public Prestito trovaPrestitoUtente(Biblioteca b, Utente u,Libro l)
	{
		Prestito p=null;
		long idb=b.getId_Biblioteca();
		long idu=u.getId_Utente();
		long idl=l.getId_Libro();
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			
			Query query=session.createQuery("from Prestito where Biblio_id_Biblioteca=:x1 and Utente_id_utente=:x2 and Libro_id_Libro=:x3");
			query.setLong("x1", idb);
			query.setLong("x2", idu);
			query.setLong("x3", idl);
		
			p=(Prestito) query.uniqueResult();

			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		return p;
	}

public boolean eliminaPrestito(Prestito p)
{
	boolean result=false;

	Session session =HibernateUtil.openSession();
	Transaction tx=null;

	try{
		tx=session.getTransaction();
		tx.begin();
		
		session.delete(p);
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
