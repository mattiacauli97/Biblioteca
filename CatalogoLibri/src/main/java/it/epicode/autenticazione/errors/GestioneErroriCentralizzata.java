package it.epicode.autenticazione.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GestioneErroriCentralizzata {

	@ExceptionHandler(GiaEsistenteException.class)
	protected ResponseEntity entitaGiaEsistente(GiaEsistenteException ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(NonTrovatoException.class)
	protected ResponseEntity entitaNonTrovata(NonTrovatoException ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}
