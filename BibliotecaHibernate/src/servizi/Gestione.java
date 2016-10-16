package servizi;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.*;
import dao.*;
import utility.*;


public class Gestione {
	//Limite di tempo per restituire un libro
	private static final long tempoLimite = 86400000;
	BibliotecaDao bdao=new BibliotecaDao();
	UtenteDao udao=new UtenteDao();
	LibroDao ldao=new LibroDao();
	PrestitoDao pdao=new PrestitoDao();

	//metodo per aggiungere una biblioteca al database
	public boolean addBiblioteca(String nome){
		Biblioteca b=new Biblioteca(nome);

		boolean result=bdao.creaBiblioteca(b);
		return result;
	}

	//metodo per aggiungere un utente alla lista utenti di una biblioteca e al database. Lancia un'eccezione se l'utente
	//Ë gi‡ registrato alla bilbioteca.
	public boolean addUtente(Biblioteca b,String nome, String cognome, String codiceFiscale) throws UtenteGi‡Inserito {

		for(Utente v : b.getUtenti()) {
			if(v.getCodiceFiscale().equals(codiceFiscale))
			{
				throw new UtenteGi‡Inserito();
			}
		}
		
		Utente u=new Utente(nome,cognome,codiceFiscale);
		b.addUtente(u);
		u.addBiblioteca(b);
		boolean result= udao.creaUtente(u);
		bdao.aggiornaBiblioteca(b);

		return result;
	}
	
	//metodo per aggiungere un libro alla lista libri della biblioteca e per aggiornarne il numero di copie
	//Il serial number deve essere diverso da biblioteca a biblioteca
	public boolean addLibro(Biblioteca b,String titolo, String autore, String serialNumber, int copie) {
		
		boolean result= false;
		for(Libro l : b.getLibri()) {
			if(l.getSerialNumber().equals(serialNumber))
			{
				l.setCopieTotali(l.getCopieTotali()+copie);
				l.setCopieDisponibili(l.getCopieDisponibili()+copie);
				ldao.aggiornaLibro(l);
				result=true;
				break;
			}
		}
		if(result==false)
		{
		Libro ll= new Libro(titolo,autore,serialNumber);
		ll.setCopieTotali(copie);
		ll.setCopieDisponibili(copie);
		b.addLibro(ll);
		ll.addBiblioteca(b);
		result=ldao.creaLibro(ll);
		bdao.aggiornaBiblioteca(b);
		}
		
		return result;
	}
	
	//metodo per chiedere un prestito. Il prestito non viene concesso se:
	//il libro non Ë presente in biblioteca o non ci sono copie disponibili;
	//l'utente non Ë iscritto alla biblioteca o ha gi‡ tre prestiti attivi;
	//l'utente ha un prestito scaduto
	public boolean prestitoLibro(Biblioteca b, String codiceFiscale, String serialNumber) {
		boolean result =false;
		Map<String,Utente> utentiBib= utentiBiblioteca(b);
		Map<String,Libro> libriBib= libriBiblioteca(b);

		if(libriBib.containsKey(serialNumber) && libriBib.get(serialNumber).getCopieDisponibili()>0)
		{
			Libro l=libriBib.get(serialNumber);
			if(utentiBib.containsKey(codiceFiscale) && utentiBib.get(codiceFiscale).getPrestitiUtente().size()<3)
			{
				Utente u=utentiBib.get(codiceFiscale);
				if(!u.getPrestitiUtente().isEmpty())
				{
					Calendar oggi=new GregorianCalendar();
					Date prestito=pdao.trovaPrestitiUtente(b, u).get(0).getData();
					if((oggi.getTimeInMillis()-prestito.getTime())<tempoLimite)
					{
						Prestito p=new Prestito();
						p.setBiblio(b);
						p.setLibro(l);
						p.setUtente(u);
						Date data= new Date(oggi.getTimeInMillis());
						p.setData(data);
						l.setCopieDisponibili(l.getCopieDisponibili()-1);
						b.addPrestito(p);
						u.addPrestito(p);
						l.addPrestito(p);
						bdao.aggiornaBiblioteca(b);
						ldao.aggiornaLibro(l);
						udao.aggiornaUtente(u);
						result=true;

					}
				}
				else{
					
					Prestito p=new Prestito();
					p.setBiblio(b);
					p.setLibro(l);
					p.setUtente(u);
					Calendar oggi=new GregorianCalendar();
					Date data= new Date(oggi.getTimeInMillis());
					p.setData(data);
					l.setCopieDisponibili(l.getCopieDisponibili()-1);
					b.addPrestito(p);
					u.addPrestito(p);
					l.addPrestito(p);
					bdao.aggiornaBiblioteca(b);
					ldao.aggiornaLibro(l);
					udao.aggiornaUtente(u);
					result=true;
				}
			}
		}
		
		return result;
		
	}
	
	//metodo per restituire un prestito. Controlla che il libro sia registrato in biblioteca, che sia presente
	//nei prestiti dell'utente e che l'utente sia iscritto alla biblioteca
	public boolean restituisciPrestito(Biblioteca b, String codiceFiscale, String serialNumber){
		boolean result=false;
		Map<String,Utente> utentiBib= utentiBiblioteca(b);
		Map<String,Libro> libriBib= libriBiblioteca(b);
		if(libriBib.containsKey(serialNumber) && utentiBib.containsKey(codiceFiscale))
		{
			Libro l=libriBib.get(serialNumber);
			Utente u=utentiBib.get(codiceFiscale);
			Prestito p=pdao.trovaPrestitoUtente(b, u, l);
			if(p!=null)
			{
				pdao.eliminaPrestito(p);
				l.setCopieDisponibili(l.getCopieDisponibili()+1);	
				result=true;
			}
		}
		
		return result;
	}

	private Map<String,Libro> libriBiblioteca(Biblioteca b)
	{
		Map<String,Libro> libri = new TreeMap<String,Libro>();
		for(Libro l: b.getLibri())
		{
			libri.put(l.getSerialNumber(), l);
		}
		
		return libri;
	}
	
	private Map<String,Utente> utentiBiblioteca(Biblioteca b)
	{
		Map<String,Utente> utenti = new TreeMap<String,Utente>();
		for(Utente u: b.getUtenti())
		{
			utenti.put(u.getCodiceFiscale(),u);
		}
		
		return utenti;
	}

	
	
}
