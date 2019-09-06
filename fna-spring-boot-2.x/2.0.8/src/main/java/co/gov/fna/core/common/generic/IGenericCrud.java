package co.gov.fna.core.common.generic;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * The Interface ICrudService.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @version 1.0
 * @version 1.0
 * @param <T> the generic type
 * @param <ID> the generic type
 * @since 1.0
 */
public interface IGenericCrud<T, ID extends Serializable> {
	
	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @return the optional
	 */
	Optional<T> create(T entity);		
	
	/**
	 * Creates the.
	 *
	 * @param entities the entities
	 * @return the optional
	 */
	Optional<Collection<T>> createAll(Collection<T> entities);
		
	
	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @return the optional
	 */
	Optional<T> deleteById(ID id);
	
	/**
	 * Modify.
	 *
	 * @param entity the entity
	 * @return the optional
	 */
	Optional<T> modify(T entity);
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	Optional<T> findById(ID id);
	
	/**
	 * Find all.
	 *
	 * @return the collection
	 */
	Collection<T> findAll();

}
