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
@Table(name = "Usuario")
public class Usuario extends EntidadeApp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960198405945450687L;
	private int idUsuario;
	private Cargo cargo;
	private String nome;
	private String email;	

	private String rg;
	private String cpf;
	private Date dataAdmissao;
	private String telefone;
	private String senha;
	private String endereco;
	private boolean isAdministrador;
	private boolean isAtivo ;

	public Usuario() {
	}

	public Usuario(Cargo cargo) {
		this.cargo = cargo;
	}

	public Usuario(Cargo cargo, String nome, String rg, String cpf,
			Date dataAdmissao, String telefone, String senha, String endereco) {
		this.cargo = cargo;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataAdmissao = dataAdmissao;
		this.telefone = telefone;
		this.senha = senha;
		this.endereco = endereco;
	}

	@Id
	@Column(name = "idUsuario", unique = true, nullable = false)
	@GeneratedValue ( strategy = GenerationType.AUTO )
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCargo", nullable = false)
	public Cargo getCargo() {
		if(cargo == null){
			cargo = new Cargo();
		}
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Column(name = "Nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "RG", length = 45)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "CPF", length = 45)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DataAdmissao", length = 10)
	public Date getDataAdmissao() {
		return this.dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	@Column(name = "Telefone", length = 45)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "Senha", length = 45)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "Endereco")
	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "Email", unique=true)
	public String getEmail( ) {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "isAdministrador")
	public boolean isAdministrador( ) {
		return isAdministrador;
	}

	public void setAdministrador(boolean isAdministrador) {
		this.isAdministrador = isAdministrador;
	}

	@Column(name = "isAtivo")
	public boolean isAtivo( ) {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

}
