package com.clsolucoes.siscoor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.clsolucoes.siscoor.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author neidijane Loiola
 *
 */
@Entity
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cel_aluno")
	private String celAluno;

	@Column(name = "nome_aluno")
	private String nomeAluno;

	@Column(name = "email_aluno", unique = true)
	private String emailAluno;

	@Column(name = "cpf_aluno", unique = true)
	private String cpfAluno;

	@JsonIgnore
	private String senha;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();

//	//cria neste momento a tabela relacionamento
//	@JsonIgnore
//	@ManyToMany
//	@JoinTable(name = "ALUNO_GRUPO",
//		joinColumns = @JoinColumn(name = "id_aluno"),
//		inverseJoinColumns = @JoinColumn(name = "id_grupo"),
//		column
//	)
//	private List<Grupo> grupos = new ArrayList<>();

	public Aluno() {
		addPerfil(Perfil.ESTAGIARIO);
	}

	public Aluno(Integer id, String nome, String email, String cpf, String senha) {
		super();
		this.id = id;
		this.nomeAluno = nome;
		this.emailAluno = email;
		this.cpfAluno = cpf;
		this.senha = senha;
		addPerfil(Perfil.ESTAGIARIO);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCelAluno() {
		return this.celAluno;
	}

	public void setCelAluno(String celAluno) {
		this.celAluno = celAluno;
	}

	public String getNomeAluno() {
		return this.nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getEmailAluno() {
		return emailAluno;
	}

	public void setEmailAluno(String emailAluno) {
		this.emailAluno = emailAluno;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
