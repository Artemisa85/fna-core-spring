package co.gov.fna.core.common.exception;


/**
 * Enumeración que contiene los código de errores genericos 
 * manejados en el core 
 */
public enum ErrorCode {

	/** The data entity validator error. */
	DATA_ENTITY_VALIDATOR_ERROR("Validación fallida en los atributos de la entidad"),

	/** The find not found. */
	FIND_NOT_FOUND_ERROR("Recurso no encontrado");
	/**
	 * Instantiates a new error code.
	 *
	 * @param message the message
	 */
	private ErrorCode(final String message) {		
		this.message = message;
	}
	
	/** The message. */
	private String message;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}	
	
}
