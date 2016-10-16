import java.util.Calendar;
import java.util.GregorianCalendar;

import servizi.Gestione;
import servizi.UtenteGi‡Inserito;
import model.Biblioteca;
import model.Libro;
import model.Prestito;
import model.Utente;
import dao.BibliotecaDao;
import dao.LibroDao;
import dao.PrestitoDao;
import dao.UtenteDao;


public class prova {

	public static void main(String[] args) {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();
		UtenteDao udao= new UtenteDao();
		LibroDao ldao=new LibroDao();
		PrestitoDao pdao=new PrestitoDao();
		g.addBiblioteca("Calvino");
		Biblioteca b= bdao.trovaBiblioteca("Calvino");
		try {
			g.addUtente(b, "AAAAA", "aaaaa", "aa32jfhs9");
		} catch (UtenteGi‡Inserito e) {
			e.printStackTrace();
		}
		for(Utente v : b.getUtenti()) {
			System.out.println(v.getCodiceFiscale());
		}

		g.addLibro(b, "Titolo1", "Autore1", "TH302", 3);
		g.prestitoLibro(b, "aa32jfhs9", "TH302");
		
		Utente u=udao.trovaUtente("aa32jfhs9");
		Libro l=ldao.trovaLibro("TH302");
		Prestito p=pdao.trovaPrestitoUtente(b, u, l);
		System.out.println(p.getData());
	}

}
