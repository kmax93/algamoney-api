package com.algamoney.algamoneyapi.lancamento.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algamoney.algamoneyapi.lancamento.filtro.LancamentoFiltro;
import com.algamoney.algamoneyapi.lancamento.model.Lancamento;
import com.algamoney.algamoneyapi.lancamento.service.LancamentoService;
import com.algamoney.algamoneyapi.pessoa.exception.PessoaInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	private LancamentoService lancamentoService;
	
	private ApplicationEventPublisher publisher;
	
	private MessageSource messageSource;
	
	public LancamentoController(LancamentoService lancamentoService, ApplicationEventPublisher publisher, MessageSource messageSource) {
		this.lancamentoService = lancamentoService;
		this.publisher = publisher;
		this.messageSource = messageSource;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<LancamentoVO> listarLancamentos(LancamentoFiltro filtro, Pageable pageable) {
		return lancamentoService.listarLancamentos(filtro, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<LancamentoVO> criarLancamento(@Valid @RequestBody LancamentoVO lancamentoVO, HttpServletResponse resp) {
		LancamentoVO lancamentoVOSalvo = lancamentoService.criarLancamento(lancamentoVO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, resp, lancamentoVOSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoVOSalvo);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public ResponseEntity<LancamentoVO> buscarLancamentoPorId(@PathVariable Long id) {
		Lancamento lancamento = lancamentoService.buscarLancamentoPorId(id);
		
		if (lancamento == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new LancamentoVO(lancamento, lancamento.getCategoria(), lancamento.getPessoa()));
	}
	
	@ExceptionHandler({PessoaInativaException.class})
	protected ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex, WebRequest request) {
		
		String mensagemRespostaUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemRespostaDev = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemRespostaUsuario, mensagemRespostaDev));
		
		return ResponseEntity.badRequest().body(erros);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_EDITAR_LANCAMENTO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPessoa(@PathVariable Long id) {
		lancamentoService.deletarLancamento(id);
	}
	
	
}