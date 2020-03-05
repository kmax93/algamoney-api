package com.algamoney.algamoneyapi.lancamento.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algamoney.algamoneyapi.lancamento.controller.LancamentoVO;
import com.algamoney.algamoneyapi.lancamento.filtro.LancamentoFiltro;
import com.algamoney.algamoneyapi.lancamento.model.Lancamento;
import com.algamoney.algamoneyapi.lancamento.model.Lancamento_;
import com.algamoney.algamoneyapi.lancamento.repository.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<LancamentoVO> filtrar(LancamentoFiltro filtro, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filtro, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = em.createQuery(criteria);
		adicionarRestricoesPaginacao(query, pageable);
		
		return new PageImpl<LancamentoVO>(traduzirLancamentos(query.getResultList()), pageable, total(filtro));
	}

	private Predicate[] criarRestricoes(LancamentoFiltro filtro, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%" + filtro.getDescricao() + "%"));
		}
		
		if (filtro.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.getDataVencimentoDe()));	
		}
		
		if (filtro.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
		
	}
	
	private void adicionarRestricoesPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalPorPagina);
	}
	
	private Long total(LancamentoFiltro filtro) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filtro, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return em.createQuery(criteria).getSingleResult();
		
	}

	private List<LancamentoVO> traduzirLancamentos(List<Lancamento> lancamentos) {
		List<LancamentoVO> lancamentosVO = new ArrayList<LancamentoVO>(lancamentos.size());
		
		for (Lancamento l : lancamentos) {
			lancamentosVO.add(new LancamentoVO(l, l.getCategoria(), l.getPessoa()));
		}
		
		return lancamentosVO;
	}

}