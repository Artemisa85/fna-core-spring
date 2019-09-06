package co.gov.fna.core.common.exception;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.gov.fna.core.common.exception.ErrorDetail;

/**
 * The Class ServicioException.
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FnaException extends RuntimeException {
				
	/** The http status. */
	private HttpStatus status;	
			
	/** The error detail. */
	private ErrorDetail errorDetail;
		
	/**
	 * Instantiates a new fna exception.
	 *
	 * @param message the message
	 */
	public FnaException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new fna exception.
	 *
	 * @param httpStatus the http status
	 * @param message the message
	 */
	public FnaException(HttpStatus status, String message) {
		super(message);		
		this.status = status;
	}
		
	/**
	 * Instantiates a new fna exception.
	 *
	 * @param errorDetail the error detail
	 * @param httpStatus the http status
	 * @param message the message
	 */
	public FnaException(ErrorDetail errorDetail, HttpStatus status) {
		
		super(errorDetail.getMessage());		
		this.status = status;
		this.errorDetail = errorDetail;
		if(Objects.nonNull(this.status) && Objects.nonNull(this.errorDetail)) {
			this.errorDetail.setStatus(this.status.value());
		}
	}	
		
	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Gets the error detail.
	 *
	 * @return the error detail
	 */
	public ErrorDetail getErrorDetail() {
		return errorDetail;
	}
		
}
