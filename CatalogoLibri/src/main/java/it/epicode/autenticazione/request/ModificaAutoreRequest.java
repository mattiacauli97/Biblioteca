package it.epicode.autenticazione.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ModificaAutoreRequest {

	private int id;
	private String nome;
	private String cognome;
	
}
