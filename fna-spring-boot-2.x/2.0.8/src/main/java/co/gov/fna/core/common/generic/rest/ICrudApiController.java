/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.core.common.generic.rest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * The Interface IGenericCrudApiController.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @version 1.0
 * @version 1.0
 * @param <T> the generic type
 * @param <ID> the generic type
 * @since 1.0
 */
public interface ICrudApiController<T, ID extends Serializable> {
	
	/**
	 * Permite crear un registro en la base de datos.
	 *
	 * @param <R> the generic type
	 * @param dto the dto
	 * @return the response entity
	 */
	@ResponseStatus(code=HttpStatus.CREATED)
	ResponseEntity<FnaResponse<FnaDtoResult>> create(@RequestBody final T dto);
		
	
	/**
	 * Permite remover o deshabilitar un registro en la base de datos.
	 *
	 * @param <R> the generic type
	 * @param id the id
	 * @return the response entity
	 */
	@ResponseStatus(code=HttpStatus.OK)
	ResponseEntity<FnaResponse<FnaDtoResult>> deleteById(@PathVariable final ID id);
	

	/**
	 * Permite realizar modificación de un registro en la base de datos.
	 *
	 * @param <R> the generic type
	 * @param dto the dto
	 * @return the response entity
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> modify(@RequestBody final T dto);
			
	/**
	 * Perimite encontrar un objeto a través de su identificador - Find by id.
	 *
	 * @param <R> the generic type
	 * @param id the id
	 * @return the response entity
	 */
	@ResponseStatus(code=HttpStatus.OK)
	ResponseEntity<FnaResponse<FnaDtoResult>> findById(@PathVariable final ID id);
			
	/**
	 * Permite obtener todos los registro de una entidad que se encuentran en la base de datos.
	 *
	 * @param <R> the generic type
	 * @return the response entity
	 */
	@ResponseStatus(code=HttpStatus.OK)
	ResponseEntity<FnaResponse<FnaDtoResult>> findAll();
}
