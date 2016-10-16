package dao;

import hibernateUtil.HibernateUtil;
import model.Biblioteca;
import model.Libro;
import model.Utente;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LibroDao {

	public boolean creaLibro(Libro l) {
		boolean result=false;

		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			
			session.persist(l);
			result=true;
			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		
		return result;
	}
	
	public Libro trovaLibro(String serialNumber)
	{
		Libro l=null;
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();


			Query query= session.createQuery("from Libro where serialNumber=:x");
			query.setString("x", serialNumber);
			l=(Libro) query.uniqueResult();
			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		return l;
	}

	public boolean aggiornaLibro(Libro l) {
		boolean result=false;
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			session.update(l);

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
