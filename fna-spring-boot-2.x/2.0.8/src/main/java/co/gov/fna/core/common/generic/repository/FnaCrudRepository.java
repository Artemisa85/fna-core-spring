/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.core.common.generic.repository;

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
import org.springframework.transaction.annotation.Transactional;

import co.gov.fna.core.common.exception.ErrorCode;
import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.core.common.exception.ValidationError;

/**
 * The Interface FnaCrudRepository.
 *
 * @author <a href="genrique.garcia@iecisa.com"> Guillermo Enrique García Carrasquilla</a>
 * @version 1.0
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public interface FnaCrudRepository<T, ID> extends PagingAndSortingRepository<T, ID>

{

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @return the optional
	 */
	@Transactional
	default Optional<T> create(T entity) {

		return Optional.ofNullable(save(entity));
	}

	/**
	 * Modify.
	 *
	 * @param entity the entity
	 * @return the optional
	 */
	@Transactional
	default Optional<T> modify(T entity) {

		return Optional.ofNullable(save(entity));
	}

	/**
	 * Find element by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	default Optional<T> findElementById(final ID id) {

		return findById(id);
	}

	/**
	 * Find element by id.
	 *
	 * @param id the id
	 * @param throwException the throw exception
	 * @return the optional
	 */
	default Optional<T> findElementById(final ID id, boolean throwException) {

		Optional<T> entity = findById(id);

		if (throwException && !entity.isPresent()) {
			throwNotFoundException(id);
		}
		return entity;
	}

	/**
	 * Find all element.
	 *
	 * @return the collection
	 */
	default Collection<T> findAllElement() {

		return (Collection<T>) findAll();
	}

	/**
	 * Creates the all.
	 *
	 * @param entities the entities
	 * @return the optional
	 */
	@Transactional
	default Optional<Collection<T>> createAll(Collection<T> entities) {

		return Optional.ofNullable((Collection<T>) saveAll(entities));
	}

	/**
	 * Checks if is product exists.
	 *
	 * @param id the id
	 * @return true, if is product exists
	 */
	default boolean isProductExists(final ID id) {

		if (existsById(id)) {

			return true;
		}

		throw new HttpMessageNotReadableException(
				"El [Registro con id:]  ' " + id + " ', no se encuentra registrado en el sistema.");
	}

	/**
	 * Throw not found exception.
	 *
	 * @param id the id
	 */
	default void throwNotFoundException(final ID id) {

		final ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setMessage(ErrorCode.FIND_NOT_FOUND_ERROR.getMessage());
		errorDetail.setTimestamp(Calendar.getInstance().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setCode(ErrorCode.FIND_NOT_FOUND_ERROR.name());

		Map<String, List<ValidationError>> errors = new HashMap<>();
		List<ValidationError> ve = new ArrayList<ValidationError>();

		ve.add(ValidationError.builder().code(errorDetail.getCode())
				.message(String.format("No se encontró la entidad con id igual a : [%s]", id)).build());
		errors.put(this.getClass().getSuperclass().getSimpleName(), ve);
		errorDetail.setErrors(errors);
		throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);
	}
}
