package concrete.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import concrete.exception.EmailExistenteException;
import concrete.exception.SessaoInvalidaException;
import concrete.exception.TokenInvalidoException;
import concrete.exception.UsuarioSenhaInvalidoException;
import concrete.jwt.JWTUtil;
import concrete.model.Credenciais;
import concrete.model.Usuario;
import concrete.service.UsuarioService;
import concrete.util.ApiError;

/**
 * 
 * @author fred
 *
 */
@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	private static final String MSG_401 = "Não autorizado";

	@RequestMapping(value = "/usuario", method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<Object> salvar(@RequestBody Usuario usuario) {

		List<String> erros = new ArrayList<String>();

		try {

			usuario = service.create(usuario);

		} catch (EmailExistenteException e) {

			erros.add(e.getMessage());

			ApiError apiError = new ApiError(HttpStatus.CONFLICT, erros);

			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

		}

		return new ResponseEntity<Object>(usuario, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Credenciais cred) {

		List<String> erros = new ArrayList<String>();

		Usuario usuario = null;

		try {

			usuario = service.login(cred);

		} catch (UsuarioSenhaInvalidoException e) {

			erros.add(e.getMessage());

			ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, erros);

			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

		}

		return new ResponseEntity<Object>(usuario, HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public ResponseEntity<Object> profile(@PathVariable("id") String id, HttpServletRequest request) {

		Usuario usuario = null;
		
		List<String> erros = new ArrayList<String>();
		
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, erros);

		String token = request.getHeader(JWTUtil.TOKEN_HEADER);

		if (token == null) {

			erros.add(MSG_401);
			
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

		}

		try {

			usuario = service.profile(id, token);
			
		} catch (SessaoInvalidaException e) {
			
			erros.add(e.getMessage());

			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		
		} catch (TokenInvalidoException e) {
			
			erros.add(e.getMessage());

			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		return new ResponseEntity<Object>(usuario, HttpStatus.OK);
	}
}
