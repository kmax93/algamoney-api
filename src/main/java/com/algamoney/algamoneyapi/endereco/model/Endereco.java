package com.algamoney.algamoneyapi.endereco.model;

import javax.persistence.Embeddable;

import com.algamoney.algamoneyapi.endereco.vo.EnderecoVO;

@Embeddable
public class Endereco {
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String cep;
	
	private String cidade;
	
	private String estado;
	
	public Endereco() {
		
	}

	public Endereco(String logradouro, String numero, String complemento, String cep, String cidade, String estado) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public Endereco(EnderecoVO enderecoVO) {
		this.logradouro = enderecoVO.getLogradouro();
		this.numero = enderecoVO.getNumero();
		this.complemento = enderecoVO.getComplemento();
		this.cep = enderecoVO.getCep();
		this.cidade = enderecoVO.getCidade();
		this.estado = enderecoVO.getEstado();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}