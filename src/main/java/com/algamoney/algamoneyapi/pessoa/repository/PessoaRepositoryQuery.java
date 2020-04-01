package com.algamoney.algamoneyapi.pessoa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.algamoneyapi.pessoa.vo.PessoaVO;

public interface PessoaRepositoryQuery {

	Page<PessoaVO> buscarPorNomeIlike(String nome, Pageable pageable);
	
}