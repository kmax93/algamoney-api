package com.algamoney.algamoneyapi.categoria.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algamoney.algamoneyapi.categoria.model.Categoria;

public class CategoriaVO {
	
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String nome;
	
	public CategoriaVO() {
		
	}

	public CategoriaVO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoriaVO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}