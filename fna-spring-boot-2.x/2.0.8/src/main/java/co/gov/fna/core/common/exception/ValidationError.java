package co.gov.fna.core.common.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The Class ValidationError.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @since 1.0
 * @version 1.0 
 */
@Data
@AllArgsConstructor
@Builder
public class ValidationError  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6595769106829629391L;

	/** The code. */
	private String code;
	
	/** The message. */
	private String message;	
}
