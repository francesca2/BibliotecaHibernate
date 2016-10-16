package dao;

import java.util.ArrayList;
import java.util.List;

import hibernateUtil.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.*;

public class BibliotecaDao {

	public boolean creaBiblioteca(Biblioteca b) {
		boolean result=false;
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			session.persist(b);

			result=true;
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		return result;

	}
	
	public Biblioteca trovaBiblioteca(String nome) {
		
		Biblioteca b=null;
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();


			Query query= session.createQuery("from Biblioteca where nomeBiblioteca=:x");
			query.setString("x", nome);
			b=(Biblioteca) query.uniqueResult();
			
			tx.commit();
		}catch(Exception ex){
			tx.rollback();
		}finally{
			session.close();
		}
		return b;		
	}
	
	public boolean aggiornaBiblioteca(Biblioteca b) {
		boolean result=false;
		Session session =HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			session.update(b);

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
