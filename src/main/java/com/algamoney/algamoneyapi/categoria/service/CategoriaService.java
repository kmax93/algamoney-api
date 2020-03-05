package com.algamoney.algamoneyapi.categoria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algamoney.algamoneyapi.categoria.model.Categoria;
import com.algamoney.algamoneyapi.categoria.repository.CategoriaRepository;
import com.algamoney.algamoneyapi.categoria.vo.CategoriaVO;

@Service
public class CategoriaService {
	
	private CategoriaRepository categoriaRepository;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public List<CategoriaVO> listarCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		
		return traduzirCategoriaListToVOList(categorias);
	}

	public CategoriaVO criarCategoria(CategoriaVO categoriaVO) {
		Categoria categoriaSalva = categoriaRepository.save(new Categoria(categoriaVO));
		
		return new CategoriaVO(categoriaSalva);
	}

	public CategoriaVO buscarCategoriaPorId(Long id) {
		
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
		
		boolean categoriaExiste = optionalCategoria.isPresent();
		
		if (!categoriaExiste) {
			return null;
		}
		
		return new CategoriaVO(optionalCategoria.get());
	}
	
	private List<CategoriaVO> traduzirCategoriaListToVOList(List<Categoria> categorias) {
		List<CategoriaVO> categoriasVO = new ArrayList<>(categorias.size());
		
		for (Categoria c : categorias) {
			categoriasVO.add(new CategoriaVO(c));
		}
		
		return categoriasVO;
	}
	
}