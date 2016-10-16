package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Libro {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id_Libro;
	
	private String titolo;
	private String autore;
	private String serialNumber;
	private int copieDisponibili;
	private int copieTotali;
	
	@ManyToMany
	private Set<Biblioteca> biblioteca=new HashSet<Biblioteca>();
	
	@OneToMany(mappedBy="libro",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Prestito> prestitiLibro=new HashSet<Prestito>();
	
	public Libro() {
	}

	public Libro(String titolo, String autore, String serialNumber) {
		this.titolo = titolo;
		this.autore = autore;
		this.serialNumber = serialNumber;
	}

	public long getId_Libro() {
		return Id_Libro;
	}

	public void setId_Libro(long id_Libro) {
		Id_Libro = id_Libro;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public int getCopieDisponibili() {
		return copieDisponibili;
	}

	public void setCopieDisponibili(int copieDisponibili) {
		this.copieDisponibili = copieDisponibili;
	}

	public int getCopieTotali() {
		return copieTotali;
	}

	public void setCopieTotali(int copieTotali) {
		this.copieTotali = copieTotali;
	}

	public Set<Biblioteca> getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(Set<Biblioteca> biblioteca) {
		this.biblioteca = biblioteca;
	}

	public Set<Prestito> getPrestitiLibro() {
		return prestitiLibro;
	}

	public void setPrestitiLibro(Set<Prestito> prestitiLibro) {
		this.prestitiLibro = prestitiLibro;
	}
	
	public void addBiblioteca(Biblioteca b) {
		this.biblioteca.add(b);
	}
	
	public void addPrestito(Prestito p) {
		this.prestitiLibro.add(p);
	}

}
