package it.epicode.autenticazione.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.autenticazione.errors.GiaEsistenteException;
import it.epicode.autenticazione.errors.NonTrovatoException;
import it.epicode.autenticazione.model.Autore;
import it.epicode.autenticazione.repository.AutoreRepository;
import it.epicode.autenticazione.repository.LibroRepository;
import it.epicode.autenticazione.request.InserisciAutoreELibriRequest;
import it.epicode.autenticazione.request.InserisciAutoreRequest;
import it.epicode.autenticazione.request.ModificaAutoreRequest;

@Service
public class AutoreService {

	@Autowired
	AutoreRepository ar;
	@Autowired
	LibroRepository lr;
	
	public void inserisciConLibri(InserisciAutoreELibriRequest request) {
		Autore a = new Autore();
		a.setNome(request.getNome());
		a.setCognome(request.getCognome());
		String elencoLibri = request.getLibri();
		String [] listaLibri = elencoLibri.split(",");
		for(String l : listaLibri) {
			Integer id_libro = Integer.parseInt(l);
			a.getLibri().add(lr.findById(id_libro).get());
		}
	}
	
	public void inserisci(InserisciAutoreRequest request) {
		Autore a = new Autore();
		a.setNome(request.getNome());
		a.setCognome(request.getCognome());
		ar.save(a);
	}
	
	public void modifica(ModificaAutoreRequest request) throws NonTrovatoException{
		if(!ar.existsById(request.getId())) {
			throw new NonTrovatoException("Nessun autore trovato");
		}
		Autore a = new Autore();
		a.setId(request.getId());
		a.setNome(request.getCognome());
		a.setCognome(request.getCognome());
		ar.save(a);
	}
	
	public Autore getById(int id) throws NonTrovatoException {
		if(!ar.existsById(id)) {
			throw new NonTrovatoException("Nessun autore trovato");
		}
		return ar.findById(id).get();
	}
	
	public Page getAllPaginati(Pageable page){
		return (Page) ar.findAll(page);
	}
	
	public List<Autore> getAll(){
		return (List<Autore>) ar.findAll();
	}
	
	public void elimina(int id) throws NonTrovatoException {
		if(!ar.existsById(id)) {
			throw new NonTrovatoException("autore non trovato");
		}
		ar.deleteById(id);
	}
	
}
