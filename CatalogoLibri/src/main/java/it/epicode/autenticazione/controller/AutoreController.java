package it.epicode.autenticazione.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.autenticazione.errors.NonTrovatoException;
import it.epicode.autenticazione.model.Autore;
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.request.InserisciAutoreELibriRequest;
import it.epicode.autenticazione.request.InserisciAutoreRequest;
import it.epicode.autenticazione.request.InserisciCategoriaELibriRequest;
import it.epicode.autenticazione.request.InserisciCategoriaRequest;
import it.epicode.autenticazione.request.ModificaAutoreRequest;
import it.epicode.autenticazione.request.ModificaCategoriaRequest;
import it.epicode.autenticazione.service.AutoreService;

@RestController
@RequestMapping("/autori")
@Tag(name = "Autori rest services", description = "implementazioni delle api rest degli autori")
public class AutoreController {

	@Autowired
	AutoreService as;
	
	@Operation(summary = "inserisce un autore", description = "inserisce un autore nel db")
	@ApiResponse(responseCode = "200", description = "libro inserito con successo nel db")
	@ApiResponse(responseCode = "500", description = "errore nel caricamento del libro")
	@PostMapping("/inserisci")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciAutoreRequest request, BindingResult errori) {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		as.inserisci(request);
		return ResponseEntity.ok("autore inserito con successo");
	}
	
	@Operation(summary = "inserisce un autore", description = "inserisce un autore e i suoi libri nel db")
	@PostMapping("/inserisciconlibri")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciAutoreELibriRequest request, BindingResult errori) {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		as.inserisciConLibri(request);
		return ResponseEntity.ok("autore inserito con successo");
	}
	
	@Operation(summary = "modifica un autore", description = "modifica un autore presente nel db")
	@PostMapping("/modifica")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity modifica(@Valid @RequestBody ModificaAutoreRequest request, BindingResult errori) throws NonTrovatoException {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		as.modifica(request);
		return ResponseEntity.ok("autore modificato con successo");
	}
	
	
	@GetMapping("/trovaperid/{ID}")
	public ResponseEntity trovaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		Autore a = as.getById(id);
		return ResponseEntity.ok(a);
	}
	
	@GetMapping("/trovatutti")
	public ResponseEntity trovaPerId() throws NonTrovatoException {
		List<Autore> lista = as.getAll();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/tuttiautoripaginati")
	public ResponseEntity tuttiAutoriPaginati(Pageable page) {
		return ResponseEntity.ok(as.getAllPaginati(page));
	}
	
	@DeleteMapping("/eliminaperid/{id}")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity eliminaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		as.elimina(id);
		return ResponseEntity.ok("autore eliminato con successo");
	}
	
}
