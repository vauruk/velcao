package br.com.vanderson.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity
@Table ( name = "AnimalMorto" )
public class AnimalMorto extends EntidadeApp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1195378341157566686L;
	private int idAnimalMorto;
	private String nome;
	private String localizacao;
	private Date dataRegistro;
	private Usuario usuario;
	
	public AnimalMorto(){}
	
	public AnimalMorto(int idAnimalMorto, String nome, String localizacao, Date dataRegistro, Usuario usuario) {
		this.idAnimalMorto = idAnimalMorto;
		this.nome = nome;
		this.localizacao = localizacao;
		this.dataRegistro = dataRegistro;
		this.usuario = usuario;
	}
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	public int getIdAnimalMorto() {
		return idAnimalMorto;
	}
	public void setIdAnimalMorto(int idAnimalMorto) {
		this.idAnimalMorto = idAnimalMorto;
	}
	@Column(name = "Nome", length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Column(name = "Localizacao", length = 255)
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataRegistro")
	public Date getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioRegistro", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
