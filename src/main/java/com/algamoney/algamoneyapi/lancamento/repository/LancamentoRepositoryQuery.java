package com.algamoney.algamoneyapi.lancamento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.algamoneyapi.lancamento.controller.LancamentoVO;
import com.algamoney.algamoneyapi.lancamento.filtro.LancamentoFiltro;

public interface LancamentoRepositoryQuery {
	
	Page<LancamentoVO> filtrar(LancamentoFiltro filtro, Pageable pageable);

}