package concrete.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author fred
 *
 */
public class ApiError {
	 
    private HttpStatus status;
    private List<String> mensagem;
 
    public ApiError(HttpStatus status,List<String> errors) {
        super();
        this.status = status;
        this.mensagem = errors;
    }
 
    public ApiError(HttpStatus status,String error) {
        super();
        this.status = status;
        mensagem = Arrays.asList(error);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getMensagem() {
		return mensagem;
	}

	public void setMensagem(List<String> mensagem) {
		this.mensagem = mensagem;
	}

    
}
