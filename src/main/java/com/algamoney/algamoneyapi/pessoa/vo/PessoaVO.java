package com.algamoney.algamoneyapi.pessoa.vo;

import javax.validation.constraints.NotNull;

import com.algamoney.algamoneyapi.endereco.vo.EnderecoVO;
import com.algamoney.algamoneyapi.pessoa.model.Pessoa;

public class PessoaVO {
	
	private Long id;
	
	@NotNull
	private String nome;
	
	private EnderecoVO enderecoVO;
	
	@NotNull
	private Boolean ativo;
	
	public PessoaVO() {
		
	}

	public PessoaVO(Long id, String nome, EnderecoVO enderecoVO) {
		this.id = id;
		this.nome = nome;
		this.enderecoVO = enderecoVO;
	}
	
	public PessoaVO(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.nome = pessoa.getNome();
		this.enderecoVO = new EnderecoVO(pessoa.getEndereco());
		this.ativo = pessoa.getAtivo();
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

	public EnderecoVO getEnderecoVO() {
		return enderecoVO;
	}

	public void setEnderecoVO(EnderecoVO enderecoVO) {
		this.enderecoVO = enderecoVO;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}