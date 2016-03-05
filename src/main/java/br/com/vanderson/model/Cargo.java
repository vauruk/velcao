package br.com.vanderson.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 */
@Entity
@Table(name = "Cargo")
public class Cargo extends EntidadeApp {
	
	public static void main(String[] args) {
		System.out.println(java.util.TimeZone.getDefault());
		for (String string : java.util.TimeZone.getAvailableIDs()) {
			System.out.println(string);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8396842429365548947L;
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private int idCargo;
	@Column(name = "Nome", length = 45)
	private String nome;
	@Column(name = "TipoCargo", length = 45)
	private String tipoCargo;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo")
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	@Column ( name = "isGerente" )
	private boolean	gerente;
	
	@Column ( name = "isOperacional" )
	private boolean operacional;
	
	@Column ( name = "isSupervisao" )
	private boolean supervisao;
	

	public Cargo() {
	}

	public Cargo(int idCargo) {
		this.idCargo = idCargo;
	}

	public Cargo(int idCargo, String nome, String tipoCargo,
			Set<Usuario> usuarios) {
		this.idCargo = idCargo;
		this.nome = nome;
		this.tipoCargo = tipoCargo;
		this.usuarios = usuarios;
	}

	
	public int getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}

	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getTipoCargo() {
		return this.tipoCargo;
	}

	public void setTipoCargo(String tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isGerente( ) {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}

	public boolean isOperacional( ) {
		return operacional;
	}

	public void setOperacional(boolean operacional) {
		this.operacional = operacional;
	}

	public boolean isSupervisao( ) {
		return supervisao;
	}

	public void setSupervisao(boolean supervisao) {
		this.supervisao = supervisao;
	}

}
