package it.epicode.autenticazione.runners;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.epicode.autenticazione.impl.ERole;
import it.epicode.autenticazione.impl.Role;
import it.epicode.autenticazione.impl.User;
import it.epicode.autenticazione.impl.UserRepository;
import it.epicode.autenticazione.model.Autore;
import it.epicode.autenticazione.model.Categoria;
import it.epicode.autenticazione.model.Libro;
import it.epicode.autenticazione.repository.AutoreRepository;
import it.epicode.autenticazione.repository.CategoriaRepository;
import it.epicode.autenticazione.repository.LibroRepository;

@Component
public class UserCreationRunner implements CommandLineRunner{

	@Autowired
	LibroRepository lr;
	@Autowired
	CategoriaRepository cr;
	@Autowired
	AutoreRepository ar;
	@Autowired
	UserRepository ur;
	@Autowired
	PasswordEncoder pe;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role roleAdmin = new Role();
		roleAdmin.setRoleName(ERole.ROLE_ADMIN);
		User userAdmin = new User();
		Set<Role> ruoli = new HashSet<Role>();
		ruoli.add(roleAdmin);
		userAdmin.setUsername("m.cauli5");
		userAdmin.setPassword(pe.encode("cauli123"));
		userAdmin.setEmail("mattia@gmail.it");
		userAdmin.setRoles(ruoli);
		ur.save(userAdmin);
		
		Autore a1 = new Autore();
		a1.setNome("Joanne");
		a1.setCognome("Rowling");
		
		Categoria c = new Categoria();
		c.setNome("Fantasy");
		
		Libro l = new Libro();
		l.setTitolo("Harry Potter");
		l.setAnno(1997);
		l.setPrezzo(12.0);
		l.getAutori().add(a1);
		l.getCategorie().add(c);
		
		lr.save(l);	
	}	
}
