package it.epicode.autenticazione.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class InserisciAutoreELibriRequest {

	private String nome;
	private String cognome;
	private String libri;
	
}
