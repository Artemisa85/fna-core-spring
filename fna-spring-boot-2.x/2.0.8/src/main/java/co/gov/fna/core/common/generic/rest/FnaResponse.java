/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.core.common.generic.rest;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class FnaResponse, clase usada como respuesta rest estandar para las aplicaciones
 * que implementen y usen en fna-spring-boot-2.0.8.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 * @param <T> the generic type extends to FnaDtoResult
 */
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
/**
 * Instantiates a new fna response.
 *
 * @param message the message
 * @param status the status
 * @param code the code
 * @param timestamp the timestamp
 * @param result the result
 */
@AllArgsConstructor

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Instantiates a new fna response.
 */
@NoArgsConstructor
public class FnaResponse<T extends FnaDtoResult> {

	/** The message. */
	private String message;
	
	/** The status. */
	private int status;
	
	/** The code. */
	private String code;
	
	/** The time stamp. */
	private Date timestamp;
			
	/** The result. */
	private Collection<? extends FnaDtoResult> result;	

}
