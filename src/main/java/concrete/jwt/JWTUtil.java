package concrete.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author fred
 *
 */
public class JWTUtil {
	
	private static String key = "DDCA7CDED978512C61F03456E65A3D916BDDEBA71500500A15B010BE50F2D2BF";

		    public static final String TOKEN_HEADER = "Authentication";

		    public static String create(String subject) {
		        return Jwts.builder()
		                .setSubject(subject)
		                .signWith(SignatureAlgorithm.HS512, key)
		                .compact();
		    }

		    public static Jws<Claims> decode(String token){
		        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		    }

}
