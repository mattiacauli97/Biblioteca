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
import it.epicode.autenticazione.model.Libro;
import it.epicode.autenticazione.repository.AutoreRepository;
import it.epicode.autenticazione.request.InserisciLibroRequest;
import it.epicode.autenticazione.request.ModificaLibroRequest;
import it.epicode.autenticazione.service.LibroService;

@RestController
@RequestMapping("/libri")
@Tag(name = "Libri rest services", description = "implementazioni delle api rest dei libri")
public class LibroController {
	
	@Autowired
	LibroService ls;
	@Autowired
	AutoreRepository ar;
	
	@Operation(summary = "inserisce un libro", description = "inserisce un libro nel db")
	@ApiResponse(responseCode = "200", description = "categoria inserita con successo nel db")
	@ApiResponse(responseCode = "500", description = "errore nel caricamento della categoria")
	@PostMapping("/inserisci")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisciLibro(@Valid @RequestBody InserisciLibroRequest request, BindingResult errori) {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		ls.inserisci(request);
		return ResponseEntity.ok("libro inserito con successo");
	}
	
	@Operation(summary = "trova un libro tramite l'autore", description = "trova un libro tramite l'autore presente nel db")
	@GetMapping("/findbyautore/{id}")
	public ResponseEntity findByAutore (@PathVariable(name = "id") int id_autore) throws NonTrovatoException {
		
		List<Libro> libri = ls.findByAutore(id_autore);
		return ResponseEntity.ok(libri);
	}
	
	@Operation(summary = "trova un libro tramite l'autore", description = "trova un libro tramite l'autore presente nel db")
	@GetMapping("/findbycategoria/{id}")
	public ResponseEntity findByCategoria (@PathVariable(name = "id") int id_categoria) throws NonTrovatoException {
		List<Libro> libri = ls.findBycategorie(id_categoria);
		return ResponseEntity.ok(libri);
	}
	
	@Operation(summary = "modifica un libro", description = "modifica un libro presente nel db")
	@PostMapping("/modifica")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity modifica(@Valid @RequestBody ModificaLibroRequest request, BindingResult errori) throws NonTrovatoException {
		if(errori.hasErrors()) {
			return new ResponseEntity(errori.getAllErrors().stream().map((e)->(e.getDefaultMessage())), HttpStatus.BAD_REQUEST);
		}
		ls.modifica(request);
		return ResponseEntity.ok("libro modificato con successo");
	}
	
	@Operation(summary = "trova un libro", description = "trova un libro presente nel db")
	@GetMapping("/trovaperid/{ID}")
	public ResponseEntity trovaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		Libro l = ls.findById(id);
		return ResponseEntity.ok(l);
	}
	
	@Operation(summary = "trova tutti gli libri", description = "trova tutti gli libri presenti nel db")
	@GetMapping("/trovatutti")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity trovaTutti() throws NonTrovatoException {
		List<Libro> lista = ls.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "elimina un libro", description = "elimina un libro presente nel db")
	@DeleteMapping("/elimina/{id}")
	@SecurityRequirement(name =  "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity eliminaPerId(@PathVariable(name = "id") int id) throws NonTrovatoException {
		ls.elimina(id);
		return ResponseEntity.ok("libro eliminato con successo");
	}
}
