package it.epicode.autenticazione.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.autenticazione.errors.GiaEsistenteException;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository ur;
	@Autowired
	PasswordEncoder pe;
	@Autowired
	RoleRepository rr;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
		
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken( 
				request.getUserName(), 
				request.getPassword()
		);
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities()
								.stream()
								.map(item -> item.getAuthority())
								.collect(Collectors.toList());
		
		JwtResponse jwtresp = new JwtResponse(
				jwt, 
				userDetails.getId(), 
				userDetails.getUsername(),
				roles
			);
		
		return ResponseEntity.ok(jwtresp);
		
	}
	
	@PostMapping("/loginjwt")
	public ResponseEntity<?> loginJwt(@Valid @RequestBody LoginRequest request) {
		
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken( 
				request.getUserName(), 
				request.getPassword()
		);
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return ResponseEntity.ok(jwt);
		
	}
	
	@PostMapping("/inserisciutente")
	public ResponseEntity addUser(@Valid @RequestBody User u) throws GiaEsistenteException {
		if(ur.existsByEmail(u.getEmail())) {
			throw new GiaEsistenteException("email già in uso");
		}
		if(ur.existsByUsername(u.getUsername())) {
			throw new GiaEsistenteException("username già in uso");
		}
		u.setPassword(pe.encode(u.getPassword()));
		if(u.getRoles().isEmpty()) {
			Role roleUser = new Role();
			roleUser.setRoleName(ERole.ROLE_USER);
			Set<Role> ruoli = new HashSet<Role>();
			ruoli.add(roleUser);
			u.getRoles().add(roleUser);
		}
		ur.save(u);
		return ResponseEntity.ok("utente inserito con successo");
	}
}
