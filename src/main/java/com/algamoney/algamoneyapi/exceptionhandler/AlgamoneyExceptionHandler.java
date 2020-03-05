package com.algamoney.algamoneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	private MessageSource messageSource;
	
	public AlgamoneyExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemRespostaUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemRespostaDev = ex.getCause().toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemRespostaUsuario, mensagemRespostaDev));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, criarListaErros(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	protected ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemRespostaUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemRespostaDev = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemRespostaUsuario, mensagemRespostaDev));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		
		String mensagemRespostaUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemRespostaDev = ExceptionUtils.getRootCauseMessage(ex);
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemRespostaUsuario, mensagemRespostaDev));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldErro : bindingResult.getFieldErrors()) {
			String mensagemRespostaUsuario = messageSource.getMessage(fieldErro, LocaleContextHolder.getLocale());
			String mensagemRespostaDev = fieldErro.toString();
			
			erros.add(new Erro(mensagemRespostaUsuario, mensagemRespostaDev));
		}
		
		return erros;
	}
	
	public static class Erro {
		
		private String mensagemRespostaUsuario;
		
		private String mensagemRespostaDev;

		public Erro(String mensagemRespostaUsuario, String mensagemRespostaDev) {
			this.mensagemRespostaUsuario = mensagemRespostaUsuario;
			this.mensagemRespostaDev = mensagemRespostaDev;
		}

		public String getMensagemRespostaUsuario() {
			return mensagemRespostaUsuario;
		}

		public String getMensagemRespostaDev() {
			return mensagemRespostaDev;
		}
		
	}

}