package model;

import java.sql.Date;

import javax.persistence.*;

@Entity
public class Prestito {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id_Prestito;
	
	private Date data;
	
	@ManyToOne
	private Utente utente;
	@ManyToOne
	private Libro libro;
	@ManyToOne
	private Biblioteca biblio;
	
	public Prestito() {
	}

	public long getId_Prestito() {
		return Id_Prestito;
	}

	public void setId_Prestito(long id_Prestito) {
		Id_Prestito = id_Prestito;
	}
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Biblioteca getBiblio() {
		return biblio;
	}

	public void setBiblio(Biblioteca biblio) {
		this.biblio = biblio;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
