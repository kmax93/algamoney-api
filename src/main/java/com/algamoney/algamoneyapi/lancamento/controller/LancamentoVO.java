package com.algamoney.algamoneyapi.lancamento.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.algamoney.algamoneyapi.categoria.model.Categoria;
import com.algamoney.algamoneyapi.categoria.vo.CategoriaVO;
import com.algamoney.algamoneyapi.lancamento.enumx.TipoLancamento;
import com.algamoney.algamoneyapi.lancamento.model.Lancamento;
import com.algamoney.algamoneyapi.pessoa.model.Pessoa;
import com.algamoney.algamoneyapi.pessoa.vo.PessoaVO;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LancamentoVO {
	
	private Long id;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataVencimento;
	
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	private TipoLancamento tipo;

	private CategoriaVO categoria;
	
	private PessoaVO pessoa;
	
	private Long categoriaId;
	
	private Long pessoaId;
	
	public LancamentoVO() {
		
	}

	public LancamentoVO(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor,
			String observacao, TipoLancamento tipo, CategoriaVO categoria, PessoaVO pessoa, Long categoriaId, Long pessoaId) {
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.observacao = observacao;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
		this.categoriaId = categoriaId;
		this.pessoaId = pessoaId;
	}
	
	public LancamentoVO(Lancamento lancamento, Categoria categoria, Pessoa pessoa) {
		this.id = lancamento.getId();
		this.descricao = lancamento.getDescricao();
		this.dataVencimento = lancamento.getDataVencimento();
		this.dataPagamento = lancamento.getDataPagamento();
		this.valor = lancamento.getValor();
		this.observacao = lancamento.getObservacao();
		this.tipo = lancamento.getTipo();
		this.categoria = new CategoriaVO(lancamento.getCategoria());
		this.pessoa = new PessoaVO(lancamento.getPessoa());
		this.categoriaId = lancamento.getCategoria().getId();
		this.pessoaId = lancamento.getPessoa().getId();
	}
	
	public LancamentoVO(Lancamento lancamento) {
		this.id = lancamento.getId();
		this.descricao = lancamento.getDescricao();
		this.dataVencimento = lancamento.getDataVencimento();
		this.dataPagamento = lancamento.getDataPagamento();
		this.valor = lancamento.getValor();
		this.observacao = lancamento.getObservacao();
		this.tipo = lancamento.getTipo();
		this.categoriaId = lancamento.getCategoria().getId();
		this.pessoaId = lancamento.getPessoa().getId();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public TipoLancamento getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}
	
	public CategoriaVO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaVO categoria) {
		this.categoria = categoria;
	}
	
	public PessoaVO getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(PessoaVO pessoa) {
		this.pessoa = pessoa;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

}