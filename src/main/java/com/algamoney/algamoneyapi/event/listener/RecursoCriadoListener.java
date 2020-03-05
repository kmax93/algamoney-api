package com.algamoney.algamoneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		Long id = event.getId();
		HttpServletResponse resp = event.getResp();
		
		adicionaHeaderLocation(id, resp);
	}

	private void adicionaHeaderLocation(Long id, HttpServletResponse resp) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(id).toUri();
		
		resp.addHeader("Location", uri.toASCIIString());
	}

}