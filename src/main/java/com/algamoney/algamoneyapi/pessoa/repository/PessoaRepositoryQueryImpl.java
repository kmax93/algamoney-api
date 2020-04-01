package com.algamoney.algamoneyapi.pessoa.repository;

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

import com.algamoney.algamoneyapi.pessoa.model.Pessoa;
import com.algamoney.algamoneyapi.pessoa.model.Pessoa_;
import com.algamoney.algamoneyapi.pessoa.vo.PessoaVO;

public class PessoaRepositoryQueryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<PessoaVO> buscarPorNomeIlike(String nome, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);

		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);

		TypedQuery<Pessoa> query = em.createQuery(criteria);
		adicionarRestricoesPaginacao(query, pageable);
		
		return new PageImpl<PessoaVO>(traduzirPessoas(query.getResultList()), pageable, total(nome));
	}
	
	private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(nome)) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.NOME)), "%" + nome + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
		
	}

	private void adicionarRestricoesPaginacao(TypedQuery<Pessoa> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalPorPagina;

		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalPorPagina);
	}

	private Long total(String nome) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return em.createQuery(criteria).getSingleResult();

	}

	private List<PessoaVO> traduzirPessoas(List<Pessoa> pessoas) {
		List<PessoaVO> pessoasVO = new ArrayList<PessoaVO>(pessoas.size());
		
		for (Pessoa p : pessoas) {
			pessoasVO.add(new PessoaVO(p));
		}
		
		return pessoasVO;
	}

}