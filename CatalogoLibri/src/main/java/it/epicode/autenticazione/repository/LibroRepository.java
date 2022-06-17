package it.epicode.autenticazione.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.epicode.autenticazione.model.Autore;
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, Integer> {
	
	@Query(value = "select * from libro left join libri_autori on libro.id = libri_autori.id_libro where libri_autori.id_autore = ?1", nativeQuery = true)
	public List<Libro> findByAutoreEsistente (int id);
	
	@Query(value = "select * from libro left join libri_categorie on libro.id = libri_categorie.id_libro where libri_categorie.id_categoria = ?1", nativeQuery = true)
	public List<Libro> findByCategoriaEsistente (int id);
 
}
