package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Biblioteca {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id_Biblioteca;
	
	private String nomeBiblioteca;
	
	@ManyToMany(mappedBy="biblioteca",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Libro> libri=new HashSet<Libro>();

	@ManyToMany(mappedBy="biblioteche",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Utente> utenti=new HashSet<Utente>();
	
	@OneToMany(mappedBy="biblio",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Prestito> prestiti=new HashSet<Prestito>();	
	
	public Biblioteca() {
	}

	public Biblioteca(String nomeBiblioteca) {
		this.nomeBiblioteca = nomeBiblioteca;
	}

	public long getId_Biblioteca() {
		return id_Biblioteca;
	}

	public void setId_Biblioteca(long id_Biblioteca) {
		this.id_Biblioteca = id_Biblioteca;
	}

	public String getNomeBiblioteca() {
		return nomeBiblioteca;
	}

	public void setNomeBiblioteca(String nomeBiblioteca) {
		this.nomeBiblioteca = nomeBiblioteca;
	}

	public Set<Libro> getLibri() {
		return libri;
	}

	public void setLibri(Set<Libro> libri) {
		this.libri = libri;
	}

	public Set<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(Set<Utente> utenti) {
		this.utenti = utenti;
	}

	public Set<Prestito> getPrestiti() {
		return prestiti;
	}

	public void setPrestiti(Set<Prestito> prestiti) {
		this.prestiti = prestiti;
	}
	
	public void addLibro(Libro l) {
		this.libri.add(l);
	}
	
	public void addUtente(Utente u) {
		this.utenti.add(u);
	}
	
	public void addPrestito(Prestito p) {
		this.prestiti.add(p);
	}
}
