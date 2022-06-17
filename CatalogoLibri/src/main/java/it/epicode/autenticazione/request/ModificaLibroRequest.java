package it.epicode.autenticazione.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ModificaLibroRequest {

	private int id;
	private String titolo;
	private int anno;
	private double prezzo;
	private String autori;
	private String categorie;
	
}
