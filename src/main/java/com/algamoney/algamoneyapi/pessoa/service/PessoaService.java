package com.algamoney.algamoneyapi.pessoa.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.algamoney.algamoneyapi.pessoa.model.Pessoa;
import com.algamoney.algamoneyapi.pessoa.repository.PessoaRepository;
import com.algamoney.algamoneyapi.pessoa.vo.PessoaVO;

@Service
public class PessoaService {
	
	private PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	public Page<PessoaVO> listarPessoas(@Nullable String nome, Pageable pageable) {
		return pessoaRepository.buscarPorNomeIlike(nome, pageable);
	}

	//TODO utilizar esse cara no translator para melhorar a perfomance
	//quando o front estiver  pronto
	@SuppressWarnings("unused")
	private List<PessoaVO> traduzirPessoaListToVOList(List<Pessoa> pessoas) {
		List<PessoaVO> pessoasVO = new ArrayList<PessoaVO>(pessoas.size());
		
		for (Pessoa p : pessoas) {
			pessoasVO.add(new PessoaVO(p));
		}
		
		return pessoasVO;
	}

	public PessoaVO criarPessoa(@Valid PessoaVO pessoaVO) {
		Pessoa pessoaSalva = pessoaRepository.save(new Pessoa(pessoaVO));
		
		return new PessoaVO(pessoaSalva);
	}

	public Pessoa buscarPessoaPorId(Long id) {
		Pessoa pessoa = pessoaRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return pessoa;
	}
	
	public void deletarPessoa(Long id) {
		pessoaRepository.deleteById(id);
	}
	
	public PessoaVO atualizarPessoa(Long id, PessoaVO pessoaVO) {
		Pessoa pessoaSalva = buscarPessoaPorId(id);
		PessoaVO pessoaVOSalva = new PessoaVO(pessoaSalva);
		
		BeanUtils.copyProperties(pessoaVO, pessoaVOSalva, "id");
		
		PessoaVO pessoaVOEditada = this.criarPessoa(pessoaVOSalva);
		
		return pessoaVOEditada;
	}

}