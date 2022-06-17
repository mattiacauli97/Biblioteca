package it.epicode.autenticazione.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.model.Libro;
import it.epicode.autenticazione.request.InserisciCategoriaELibriRequest;
import it.epicode.autenticazione.request.InserisciCategoriaRequest;
import it.epicode.autenticazione.request.ModificaCategoriaRequest;
import it.epicode.autenticazione.request.ModificaLibroRequest;
import it.epicode.autenticazione.service.CategoriaService;

@RestController
@RequestMapping("/categorie")
@Tag(name = "Categorie rest services", description = "implementazioni delle api rest delle categorie")
public class CategoriaController {

	@Autowired
	CategoriaService cs;
	
	@Operation(summary = "inserisce una categoria", description = "inserisce una categoria nel db")
	@ApiResponse(responseCode = "200", description = "categoria inserita con successo nel db")
	@ApiResponse(responseCode = "500", description = "errore nel caricamento della categoria")
	@PostMapping("/inserisci")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciCategoriaRequest request, BindingResult errori) {
		
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}cs.inserisci(request);
		return ResponseEntity.ok("categoria inserita con successo");
	}
	
	@Operation(summary = "inserisce una categoria", description = "inserisce una categoria e i suoi libri nel db")
	@PostMapping("/inserisciconlibri")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciCategoriaELibriRequest request, BindingResult errori) {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		cs.inserisciConLibri(request);
		return ResponseEntity.ok("categoria inserita con successo");
	}	
	
	@Operation(summary = "modifica una categoria", description = "modifica una categoria presente nel db")
	@PostMapping("/modifica")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity modifica(@Valid @RequestBody ModificaCategoriaRequest request, BindingResult errori) throws NonTrovatoException {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		cs.modifica(request);
		return ResponseEntity.ok("categoria modificato con successo");
	}
	
	@Operation(summary = "trova una categoria", description = "trova una categoria col suo id presente nel db")
	@GetMapping("/trovaperid/{ID}")
	public ResponseEntity trovaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		Categoria c = cs.findById(id);
		return ResponseEntity.ok(c);
	}
	
	@Operation(summary = "trova tutte le categorie", description = "trova tutte le categoria del db")
	@GetMapping("/trovatutti")
	public ResponseEntity trovaPerId() throws NonTrovatoException {
		List<Categoria> lista = cs.getAll();
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "elimina una categoria", description = "elimina una categoria col suo id dal db")
	@DeleteMapping("/eliminaperid/{id}")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity eliminaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		cs.elimina(id);
		return ResponseEntity.ok("categoria eliminato con successo");
	}
	
}
