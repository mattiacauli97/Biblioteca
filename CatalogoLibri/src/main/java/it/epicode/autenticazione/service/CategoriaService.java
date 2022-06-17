package it.epicode.autenticazione.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.autenticazione.errors.NonTrovatoException;
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.repository.CategoriaRepository;
import it.epicode.autenticazione.repository.LibroRepository;
import it.epicode.autenticazione.request.InserisciCategoriaELibriRequest;
import it.epicode.autenticazione.request.InserisciCategoriaRequest;
import it.epicode.autenticazione.request.ModificaCategoriaRequest;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository cr;
	@Autowired
	LibroRepository lr;
	
	public void inserisci(InserisciCategoriaRequest request) {
		Categoria c = new Categoria();
		c.setNome(request.getNome());
		cr.save(c);
	}
	
	public void inserisciConLibri(InserisciCategoriaELibriRequest request) {
		Categoria c = new Categoria();
		c.setNome(request.getNome());
		String elencoLibri = request.getLibri();
		String [] listaLibri = elencoLibri.split(",");
		for(String l : listaLibri) {
			Integer id_libro = Integer.parseInt(l);
			c.getLibri().add(lr.findById(id_libro).get());
		}
		cr.save(c);
	}
	
	public void modifica (ModificaCategoriaRequest request) throws NonTrovatoException {
		if(!cr.existsById(request.getId())) {
			throw new NonTrovatoException("Nessuna categoria trovato");
		}
		Categoria c = new Categoria();
		c.setId(request.getId());
		c.setNome(request.getNome());
		String elencoLibri = request.getLibri();
		String [] listaLibri = elencoLibri.split(",");
		for(String l : listaLibri) {
			Integer id_libro = Integer.parseInt(l);
			c.getLibri().add(lr.findById(id_libro).get());
		}
		cr.save(c);
	}
	
	public Categoria findById(int id) throws NonTrovatoException {
		if(!cr.existsById(id)) {
			throw new NonTrovatoException("Nessuna categoria trovato");
		}
		return cr.findById(id).get();
	}
	
	public List<Categoria> getAll(){
		return (List<Categoria>) cr.findAll();
	}
	
	public void elimina(int id) throws NonTrovatoException {
		if(!cr.existsById(id)) {
			throw new NonTrovatoException("categoria non trovata");
		}
		cr.deleteById(id);
	}
	
}
