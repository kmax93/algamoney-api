package com.algamoney.algamoneyapi.categoria.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoneyapi.categoria.service.CategoriaService;
import com.algamoney.algamoneyapi.categoria.vo.CategoriaVO;
import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	private CategoriaService categoriaService;
	
	private ApplicationEventPublisher publisher;
	
	public CategoriaController(CategoriaService categoriaService, ApplicationEventPublisher publisher) {
		this.categoriaService = categoriaService;
		this.publisher = publisher;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public List<CategoriaVO> listarCategorias() {
		return categoriaService.listarCategorias();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<CategoriaVO> criarCategoria(@Valid @RequestBody CategoriaVO categoriaVO, HttpServletResponse resp) {
		CategoriaVO categoriaVOSalva = categoriaService.criarCategoria(categoriaVO);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, resp, categoriaVOSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaVOSalva);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<CategoriaVO> buscarCategoriaPorId(@PathVariable Long id) {
		CategoriaVO categoriaVO = categoriaService.buscarCategoriaPorId(id);
		
		if (categoriaVO == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(categoriaVO);
	}

}