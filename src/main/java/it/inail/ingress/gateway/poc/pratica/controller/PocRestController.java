package it.inail.ingress.gateway.poc.pratica.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.inail.ingress.gateway.poc.pratica.response.PraticaListResponse;
import it.inail.ingress.gateway.poc.pratica.response.PraticaResponse;
import it.inail.ingress.gateway.poc.pratica.to.PraticaTO;


@RestController
@RequestMapping(value = "/1.0")
public class PocRestController {

    @RequestMapping(value = "/elenco", method = RequestMethod.GET, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PraticaListResponse> elencoPratiche(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PraticaListResponse resp = new PraticaListResponse();
    	resp.setListaPratiche(Arrays.asList(new PraticaTO(1, "pratica-1"), new PraticaTO(2, "pratica-2")));
        return new ResponseEntity<PraticaListResponse>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PraticaResponse> estraiPratica(@PathVariable(required = true) Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
     	PraticaResponse ret = new PraticaResponse(new PraticaTO(id, "pratica-"+id.toString()));
         return new ResponseEntity<PraticaResponse>(ret, HttpStatus.OK);
    }

    @RequestMapping(value = "/modifica", method = RequestMethod.PUT, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PraticaResponse> aggiornaPratica(@RequestBody(required = true) PraticaTO praticaTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ApplicationException {
    	PraticaResponse ret = new PraticaResponse(new PraticaTO(praticaTO.getId(), praticaTO.getPratica()));
        return new ResponseEntity<PraticaResponse>(ret, HttpStatus.OK); 
    }

    @RequestMapping(value = "/crea", method = RequestMethod.POST, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PraticaResponse> creaPratica(@RequestBody(required = true) PraticaTO praticaTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ApplicationException {
    	PraticaResponse ret = new PraticaResponse(new PraticaTO(praticaTO.getId(), praticaTO.getPratica()));
        return new ResponseEntity<PraticaResponse>(ret, HttpStatus.CREATED);
    }

}
