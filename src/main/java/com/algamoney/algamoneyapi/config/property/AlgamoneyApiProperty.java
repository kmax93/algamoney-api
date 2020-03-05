package com.algamoney.algamoneyapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private String origemPermitida = "";
	
	private final Seguranca seguranca = new Seguranca();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	} 

	public static class Seguranca {
		
		private boolean habilitaHttps;

		public boolean isHabilitaHttps() {
			return habilitaHttps;
		}

		public void setHabilitaHttps(boolean habilitaHttps) {
			this.habilitaHttps = habilitaHttps;
		}
		
	}
	
}