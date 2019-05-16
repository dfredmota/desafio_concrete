package concrete.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author fred
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Credenciais {
	
	private String email;
	
	private String password;	

}
