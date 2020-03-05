package com.algamoney.algamoneyapi.pessoa.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.pessoa.model.Pessoa;
import com.algamoney.algamoneyapi.pessoa.service.PessoaService;
import com.algamoney.algamoneyapi.pessoa.vo.PessoaVO;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	private PessoaService pessoaService;
	
	private ApplicationEventPublisher publisher;

	public PessoaController(PessoaService pessoaService, ApplicationEventPublisher publisher) {
		this.pessoaService = pessoaService;
		this.publisher = publisher;
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public List<PessoaVO> listarPessoas() {
		return pessoaService.listarPessoas();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
	public ResponseEntity<PessoaVO> criarPessoa(@Valid @RequestBody PessoaVO pessoaVO, HttpServletResponse resp) {
		PessoaVO pessoaVOSalva = pessoaService.criarPessoa(pessoaVO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, resp, pessoaVOSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaVOSalva);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public ResponseEntity<PessoaVO> buscarPessoaPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPessoaPorId(id);
		
		if (pessoa == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new PessoaVO(pessoa));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPessoa(@PathVariable Long id) {
		pessoaService.deletarPessoa(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_EDITAR_PESSOA')")
	public ResponseEntity<PessoaVO> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody PessoaVO pessoaVO) {
		PessoaVO pessoaVoAtualizada = pessoaService.atualizarPessoa(id, pessoaVO);
		
		return ResponseEntity.ok(pessoaVoAtualizada);
		
	}
	
}