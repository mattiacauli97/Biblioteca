package it.epicode.autenticazione.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull(message = "il titolo non può essere vuoto")
	private String titolo;
	@NotNull(message = "l'anno non può essere vuoto")
	private int anno;
	@NotNull(message = "il prezzo non può essere vuoto")
	private double prezzo;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
            name = "Libri_Autori",
            joinColumns = {@JoinColumn(name = "id_libro")},
            inverseJoinColumns = {@JoinColumn(name = "id_autore")}
    )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Autore> autori = new ArrayList<Autore>();
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
            name = "Libri_Categorie",
            joinColumns = {@JoinColumn(name = "id_libro")},
            inverseJoinColumns = {@JoinColumn(name = "id_categoria")}
    )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Categoria> categorie = new ArrayList<Categoria>();
	
}
