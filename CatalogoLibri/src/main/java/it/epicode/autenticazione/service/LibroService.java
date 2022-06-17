package it.epicode.autenticazione.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.autenticazione.errors.NonTrovatoException;
import it.epicode.autenticazione.model.Autore;
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.model.Libro;
import it.epicode.autenticazione.repository.AutoreRepository;
import it.epicode.autenticazione.repository.CategoriaRepository;
import it.epicode.autenticazione.repository.LibroRepository;
import it.epicode.autenticazione.request.InserisciLibroRequest;
import it.epicode.autenticazione.request.ModificaLibroRequest;

@Service
public class LibroService {

	@Autowired
	LibroRepository lr;
	@Autowired
	AutoreRepository ar;
	@Autowired
	CategoriaRepository cr;
	
	public void inserisci(InserisciLibroRequest request) {
		Libro l = new Libro();
		l.setAnno(request.getAnno());
		l.setTitolo(request.getTitolo());
		l.setPrezzo(request.getPrezzo());
		
		String elencoAutori = request.getAutori();
		String [] listaAutori = elencoAutori.split(",");
		String elencoCategorie = request.getCategorie();
		String [] listaCategorie = elencoCategorie.split(",");
		
		for(String a : listaAutori) {
			Integer id_autore = Integer.parseInt(a);
			Autore autore = ar.findById(id_autore).get();
			autore.getLibri().add(l);
			l.getAutori().add(autore);
		}
		for(String c : listaCategorie) {
			Integer id_categoria = Integer.parseInt(c);
			Categoria categoria = cr.findById(id_categoria).get();
			categoria.getLibri().add(l);
			l.getCategorie().add(categoria);
		}
		lr.save(l);
	}
	
	public List<Libro> findByAutore (int id_autore) throws NonTrovatoException{
		if(!ar.existsById(id_autore)) {
			throw new NonTrovatoException("Nessun autore trovato");
		}
		List<Libro> libri = lr.findByAutoreEsistente(id_autore);
		return libri;
	}
	
	public List<Libro> findBycategorie (int id_categoria) throws NonTrovatoException{
		if(!cr.existsById(id_categoria)) {
			throw new NonTrovatoException("Nessuna categoria trovato");
		}
		List<Libro> libri = lr.findByCategoriaEsistente(id_categoria);
		return libri;
	}
	
	public void modifica(ModificaLibroRequest request) throws NonTrovatoException {
		
		if(!lr.existsById(request.getId())) {
			throw new NonTrovatoException("Nessun libro trovato");
		}
		
		Libro l = new Libro();
		l.setAnno(request.getAnno());
		l.setTitolo(request.getTitolo());
		l.setPrezzo(request.getPrezzo());
		l.setId(request.getId());
		
		String elencoAutori = request.getAutori();
		String [] listaAutori = elencoAutori.split(",");
		String elencoCategorie = request.getCategorie();
		String [] listaCategorie = elencoCategorie.split(",");
		
		for(String a : listaAutori) {
			Integer id_autore = Integer.parseInt(a);
			Autore autore = ar.findById(id_autore).get();
			autore.getLibri().add(l);
			l.getAutori().add(autore);
		}
		for(String c : listaCategorie) {
			Integer id_categoria = Integer.parseInt(c);
			Categoria categoria = cr.findById(id_categoria).get();
			categoria.getLibri().add(l);
			l.getCategorie().add(categoria);
		}
		lr.save(l);
	}
	
	public Libro findById(int id) throws NonTrovatoException {
		if(!lr.existsById(id)) {
			throw new NonTrovatoException("Nessun libro trovato");
		}
		return lr.findById(id).get();
	}
	
	public List<Libro> findAll(){
		return (List<Libro>) lr.findAll();
	}
	
	public void elimina(int id) throws NonTrovatoException {
		if(!lr.existsById(id)) {
			throw new NonTrovatoException("libro non trovato");
		}
		lr.deleteById(id);
	}
	
}
