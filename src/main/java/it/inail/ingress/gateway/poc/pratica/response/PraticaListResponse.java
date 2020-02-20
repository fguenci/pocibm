package it.inail.ingress.gateway.poc.pratica.response;

import java.util.ArrayList;
import java.util.List;

import it.inail.ingress.gateway.poc.pratica.to.PraticaTO;

public class PraticaListResponse {
	
	List<PraticaTO> listaPratiche;

	public List<PraticaTO> getListaPratiche() {
		if(listaPratiche == null) {
			listaPratiche = new ArrayList<PraticaTO>();
		}
		return listaPratiche;
	}

	public void setListaPratiche(List<PraticaTO> listaPratiche) {
		this.listaPratiche = listaPratiche;
	}
	

}
