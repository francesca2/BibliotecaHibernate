package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Utente {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id_Utente;

	private String nome;
	private String cognome;
	private String codiceFiscale;

	@ManyToMany
	private Set<Biblioteca> biblioteche=new HashSet<Biblioteca>();

	@OneToMany(mappedBy="utente",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Prestito> prestitiUtente=new HashSet<Prestito>();

	public Utente() {
	}

	public Utente(String nome, String cognome, String codiceFiscale) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
	}

	public long getId_Utente() {
		return id_Utente;
	}

	public void setId_Utente(long id_Utente) {
		this.id_Utente = id_Utente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Set<Biblioteca> getBiblioteche() {
		return biblioteche;
	}

	public void setBiblioteche(Set<Biblioteca> biblioteche) {
		this.biblioteche = biblioteche;
	}

	public Set<Prestito> getPrestitiUtente() {
		return prestitiUtente;
	}

	public void setPrestitiUtente(Set<Prestito> prestitiUtente) {
		this.prestitiUtente = prestitiUtente;
	}

	public void addBiblioteca(Biblioteca b) {
		this.biblioteche.add(b);
	}

	public void addPrestito(Prestito p) {
		this.prestitiUtente.add(p);
	}

}
