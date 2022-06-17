package it.epicode.autenticazione.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class InserisciCategoriaELibriRequest {

	private String nome;
	private String libri;
	
}
