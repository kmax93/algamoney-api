package com.algamoney.algamoneyapi.lancamento.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algamoney.algamoneyapi.lancamento.controller.LancamentoVO;
import com.algamoney.algamoneyapi.lancamento.filtro.LancamentoFiltro;
import com.algamoney.algamoneyapi.lancamento.model.Lancamento;
import com.algamoney.algamoneyapi.lancamento.repository.LancamentoRepository;
import com.algamoney.algamoneyapi.pessoa.exception.PessoaInativaException;
import com.algamoney.algamoneyapi.pessoa.model.Pessoa;
import com.algamoney.algamoneyapi.pessoa.repository.PessoaRepository;

@Service
public class LancamentoService {

	private LancamentoRepository lancamentoRepository;
	
	private PessoaRepository pessoaRepository;
	
	public LancamentoService(LancamentoRepository lancamentoRepository, PessoaRepository pessoaRepository) {
		this.lancamentoRepository = lancamentoRepository;
		this.pessoaRepository = pessoaRepository;
	}

	public Page<LancamentoVO> listarLancamentos(LancamentoFiltro filtro, Pageable pageable) {
		return lancamentoRepository.filtrar(filtro, pageable);
	}

	
	//TODO utilizar esse cara no translator para melhorar a perfomance
	//quando o front estiver  pronto
	@SuppressWarnings("unused")
	private List<LancamentoVO> traduzirLancamentoListToVOList(List<Lancamento> lancamentos) {
		List<LancamentoVO> lancamentosVO = new ArrayList<LancamentoVO>(lancamentos.size());
		
		for (Lancamento l : lancamentos) {
			lancamentosVO.add(new LancamentoVO(l, l.getCategoria(), l.getPessoa()));
		}
		
		return lancamentosVO;
		
	}
	
	public LancamentoVO criarLancamento(@Valid LancamentoVO lancamentoVO) {
		
		Pessoa pessoa = pessoaRepository.getOne(lancamentoVO.getPessoaId());
		
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInativaException();
		}
		
		Lancamento lancamentoSalvo = lancamentoRepository.save(new Lancamento(lancamentoVO));

		return new LancamentoVO(lancamentoSalvo);
	}

	public Lancamento buscarLancamentoPorId(Long id) {
		Lancamento lancamento = lancamentoRepository.findById(id)
						.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return lancamento;
	}

	public void deletarLancamento(Long id) {
		lancamentoRepository.deleteById(id);
	}

}