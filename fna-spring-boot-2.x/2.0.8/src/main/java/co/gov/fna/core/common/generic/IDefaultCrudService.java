/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.core.common.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import co.gov.fna.core.common.exception.ErrorCode;
import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.core.common.exception.ValidationError;

/**
 * The Interface ICrudDefaultService.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @version 1.5
 * @param <T>  the generic type
 * @param <ID> the generic type
 * @param <R> the generic type
 * @since 1.5
 */
public interface IDefaultCrudService<T, ID extends Serializable, R extends PagingAndSortingRepository<T, ID>>
		extends IGenericCrud<T, ID> {

	
	/**
	 * Creates the.
	 *
	 * @param repository the repository
	 * @param entity the entity
	 * @return the optional
	 */
	default Optional<T> create(R repository, T entity) {

		return save(repository, entity);
	}
			
	/**
	 * Disabled.
	 *
	 * @param repository the repository
	 * @param id the id
	 * @return the optional
	 */
	default Optional<T> disabled(R repository, ID id) {

		return save(repository, findById(id).get());
	}

	/**
	 * Modify.
	 *
	 * @param repository the repository
	 * @param entity the entity
	 * @return the optional
	 */
	default Optional<T> modify(R repository, T entity) {

		return save(repository, entity);
	}
	
	/**
	 * Find by id.
	 *
	 * @param repository the repository
	 * @param id the id
	 * @return the optional
	 */
	default Optional<T> findById(R repository, final ID id) {
		
		return repository.findById(id); 
	}
	
	/**
	 * Find by id.
	 *
	 * @param repository the repository
	 * @param id the id
	 * @param thowException the throw exception
	 * @return the optional
	 */
	default Optional<T> findById(R repository, final ID id, boolean throwException) {

		Optional<T> entity = findById(repository,id);
		
		if(throwException && !entity.isPresent()) {
			throwNotFoundException(repository, id);
		}
		return entity; 
	}
	
	/**
	 * Find all.
	 *
	 * @param repository the repository
	 * @return the collection
	 */
	default Collection<T> findAll(R repository) {

		return (Collection<T>) repository.findAll();
	}
	
	/**
	 * Save.
	 *
	 * @param repository the repository
	 * @param entity the entity
	 * @return the optional
	 */
	default Optional<T> save(R repository, T entity) {

		return Optional.ofNullable(repository.save(entity));
	}
		
	/**
	 * Save.
	 *
	 * @param repository the repository
	 * @param entities the entities
	 * @return the optional
	 */
	default Optional<Collection<T>> createAll(R repository, Collection<T> entities){ 	
		
		return Optional.ofNullable((Collection<T>)repository.saveAll(entities));
	}
	
	/**
	 * Checks if is product exists.
	 *
	 * @param repository the repository
	 * @param id the id
	 * @return true, if is product exists
	 */
	default boolean isProductExists(R repository, final ID id) {

		if (repository.existsById(id)) {

			return true;
		}

		throw new HttpMessageNotReadableException("El [Registro con id:]  ' " + id + " ', no se encuentra registrado en el sistema.");
	}	
	
	/**
	 * Throw not found exception.
	 *
	 * @param repository the repository
	 * @param id the id
	 */
	default void throwNotFoundException(R repository, final ID id) {
		
		final ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setMessage(ErrorCode.FIND_NOT_FOUND_ERROR.getMessage());
		errorDetail.setTimestamp(Calendar.getInstance().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setCode(ErrorCode.FIND_NOT_FOUND_ERROR.name());
		
		Map<String, List<ValidationError>> errors = new HashMap<>();
		List<ValidationError> ve = new ArrayList<ValidationError>();
		
		ve.add(ValidationError.builder().code(errorDetail.getCode())
				.message(String.format("No se encontró la entidad con id igual a : [%s]", id)).build());
		repository.getClass().getClassLoader();
		errors.put(repository.getClass().getSuperclass().getSimpleName(), ve);			
		errorDetail.setErrors(errors);			
		throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);	
	}
}
