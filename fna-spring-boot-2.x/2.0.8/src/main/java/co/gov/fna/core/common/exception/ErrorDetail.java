package co.gov.fna.core.common.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The Class DetailError.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @since 1.0
 * @version 1.5 
 */
@Data
@AllArgsConstructor
@Builder
public class ErrorDetail {
	
	/** The message. */
	private String message;
	
	/** The status. */
	private int status;
	
	/** The code. */
	private String code;
	
	/** The time stamp. */
	private Date timestamp;
	
	/** The path. */
	private String path;
					
	/** The errors. *///	
	private Map<String, List<ValidationError>> errors;
	
	/**
	 * Instantiates a new detail error.
	 */
	public ErrorDetail() { 
		this.errors = new HashMap<String, List<ValidationError>>();
	}
	
}
