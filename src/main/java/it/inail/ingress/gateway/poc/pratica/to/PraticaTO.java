package it.inail.ingress.gateway.poc.pratica.to;

public class PraticaTO {

	private final long id;
	private final String pratica;

	public PraticaTO(long id, String pratica) {
		this.id = id;
		this.pratica = pratica;
	}

	public long getId() {
		return id;
	}

	public String getPratica() {
		return pratica;
	}

}
