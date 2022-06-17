package it.epicode.autenticazione.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.epicode.autenticazione.model.Autore;

public interface AutoreRepository extends PagingAndSortingRepository<Autore, Integer> {
	
	public List<Autore> findByNomeContaining(String nome);
	
}
