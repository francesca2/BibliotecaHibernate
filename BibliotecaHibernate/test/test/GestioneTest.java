package test;
import static org.junit.Assert.*;
import model.Biblioteca;
import model.Libro;
import model.Utente;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import servizi.Gestione;
import servizi.UtenteGi‡Inserito;
import dao.BibliotecaDao;
import dao.LibroDao;
import dao.UtenteDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestioneTest {

	@Test
	public void test_1_CreaBiblioteca() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();

		boolean b=g.addBiblioteca("Calvino");
		Biblioteca bib=bdao.trovaBiblioteca("Calvino");

		assertTrue(b);
		assertNotNull(bib);
	}

	@Test
	public void test_2_AggiungiVoce() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		boolean result=false;
		try {
			result = g.addUtente(b, "AAAAA", "aaaaa", "aa32jfhs9");
		} catch (UtenteGi‡Inserito e) {
			e.printStackTrace();
		}

		assertTrue(result);
	}

	@Test
	public void test_3_AggiungiVoce() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		try {
			g.addUtente(b, "AAAAA", "aaaaa", "aa32jfhs9");
			fail("L'utente dovrebbe essere gi‡ inserito");
		} catch (UtenteGi‡Inserito e) {
			e.printStackTrace();
		}


	}

	@Test
	public void test_4_AggiungiLibro() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		boolean result=g.addLibro(b, "Titolo1", "Autore1", "TH302", 3);

		assertTrue(result);
	}

	@Test
	public void test_5_AggiungiLibro() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();
		LibroDao ldao=new LibroDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		g.addLibro(b, "Titolo1", "Autore1", "TH302", 3);
		Libro l= ldao.trovaLibro("TH302");

		assertEquals(l.getCopieTotali(),6);
	}

	@Test
	public void test_6_ChiediPrestito() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();
		LibroDao ldao=new LibroDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		boolean result=g.prestitoLibro(b, "aa32jfhs9", "TH302");

		assertTrue(result);
	}

	@Test
	public void test_7_ChiediPrestito() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();
		LibroDao ldao=new LibroDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");
		g.addLibro(b, "Titolo2", "Autore2", "BZ221", 2);
		g.addLibro(b, "Titolo3", "Autore3", "SL871", 2);
		g.addLibro(b, "Titolo4", "Autore4", "PZ345", 2);
		boolean result1=g.prestitoLibro(b, "aa32jfhs9", "BZ221");
		assertTrue(result1);
		boolean result2=g.prestitoLibro(b, "aa32jfhs9", "SL871");
		assertTrue(result2);
		boolean result3=g.prestitoLibro(b, "aa32jfhs9", "PZ345");
		assertFalse(result3);
	}
	
	@Test
	public void test_8_RestituisciPrestito() {
		Gestione g=new Gestione();
		BibliotecaDao bdao=new BibliotecaDao();
		LibroDao ldao=new LibroDao();
		UtenteDao udao=new UtenteDao();

		Biblioteca b=bdao.trovaBiblioteca("Calvino");

		boolean result=g.restituisciPrestito(b, "aa32jfhs9", "BZ221");
		assertTrue(result);
		Utente u=udao.trovaUtente("aa32jfhs9");
		assertEquals(u.getPrestitiUtente().size(),2);

	}

}
