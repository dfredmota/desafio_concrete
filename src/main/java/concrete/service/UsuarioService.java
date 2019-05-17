package concrete.service;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import concrete.dao.UsuarioDAO;
import concrete.exception.EmailExistenteException;
import concrete.exception.SessaoInvalidaException;
import concrete.exception.TokenInvalidoException;
import concrete.exception.UsuarioSenhaInvalidoException;
import concrete.jwt.JWTUtil;
import concrete.model.Credenciais;
import concrete.model.Usuario;


/**
 * 
 * @author fred
 *
 */
@Service("usuarioService")
public class UsuarioService {

	long MAX_DURATION = MILLISECONDS.convert(30, MINUTES);
	
	private static final String MSG_401 = "Não autorizado";
	
	private static final String INVALID_SESSION = "Sessão inválida";
	
	private static final String INVALID_USER_PASS = "Usuário e/ou senha inválidos";
	
	private static final String EMAIL_ALREADY_EXIST = "E-mail já existente";
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	

	public Usuario getById(Long id) {
		return usuarioDAO.getById(id);
	}

	public Usuario create(Usuario usuario) throws EmailExistenteException {
				
		Usuario validUser = usuarioDAO.getUsuarioPorEmail(usuario.getEmail());
		
		if(validUser == null) {
			
			String token = JWTUtil.create(usuario.getName());
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			
			usuario.setToken(token);
			
			usuario = usuarioDAO.save(usuario);
			
			return usuario;
			
		}else {
			
			throw new EmailExistenteException(EMAIL_ALREADY_EXIST);
			
		}


	}
	
	public Usuario login(Credenciais cred) throws UsuarioSenhaInvalidoException {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		Usuario validUser = usuarioDAO.getUsuarioPorEmail(cred.getEmail());
		
		if (validUser == null || !passwordEncoder.matches(cred.getPassword(), validUser.getPassword())) {
			throw new UsuarioSenhaInvalidoException(INVALID_USER_PASS);
		}
		
		validUser.setLastLogin(new Date());
		validUser.setModified(new Date());
		
		validUser = usuarioDAO.update(validUser);
		
		return validUser;
		
	}
	
	public Usuario profile(String id, String tokenJwt) throws TokenInvalidoException, SessaoInvalidaException{
		
		
		Usuario usuario = usuarioDAO.getById(new Long(id));
		
		if(!usuario.getToken().equals(tokenJwt)) {
			
			throw new TokenInvalidoException(MSG_401);
		}
		
		long duration = new Date().getTime() - usuario.getLastLogin().getTime();
		
		if (duration <= MAX_DURATION) {
		    
			throw new SessaoInvalidaException(INVALID_SESSION);
		}
		
		return usuario;		
		
	}
	
	public Usuario getUsuarioPorEmail(String email) {
		return usuarioDAO.getUsuarioPorEmail(email);
	}

}
