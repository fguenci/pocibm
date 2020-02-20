package it.inail.ingress.gateway.poc.pratica.response;

import it.inail.ingress.gateway.poc.pratica.to.PraticaTO;

public class PraticaResponse {

	private PraticaTO pratica;

	public PraticaResponse(PraticaTO pratica) {
		this.setPratica(pratica);
	}

	public PraticaTO getPratica() {
		return pratica;
	}

	public void setPratica(PraticaTO pratica) {
		this.pratica = pratica;
	}
}
