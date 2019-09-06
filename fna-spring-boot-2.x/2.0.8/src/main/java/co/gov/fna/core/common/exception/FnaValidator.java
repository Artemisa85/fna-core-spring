package co.gov.fna.core.common.exception;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;
 
/**
 * The Class ValidatorUtil.
 *
 * @author guillermo.garcia
 * @version 1.0
 * @since 24-oct-2015.
 */

/** The Constant log. */
@Slf4j
public class FnaValidator {
	
		
	/**
	 *  The Constant controlUTF8.
	 *
	 * @return the validator
	 */
	
	/**
	 * Gets the validator.
	 *
	 * @param locale the locale
	 * @return the validator
	 */
	private Validator getValidator() {		
			return Validation.byDefaultProvider()
	        .configure()	        
	        .buildValidatorFactory()
	        .getValidator();  		
	}

	/**
	 * Validate entity.
	 *
	 * @param entityModel the entity model
	 */
	public void validateEntity(Object entityModel) //throws AppException 
	{		
		Set<ConstraintViolation<Object>> constraintViolations = getValidator().validate(entityModel);
			
		if (!constraintViolations.isEmpty()) {
			
			List<String> constraintViolationMessages = new ArrayList<String>();
			
			Iterator<ConstraintViolation<Object>> constraintViolationIterator = constraintViolations.iterator();
			
			Map<String, List<ValidationError>> mapError = new HashMap<>();
			
			while (constraintViolationIterator.hasNext()) {
				
				ConstraintViolation<Object> constraintViolation = constraintViolationIterator.next();				
				ValidationError validationError = ValidationError.builder().build();							
				validationError.setCode(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
				validationError.setMessage(constraintViolation.getMessage());
				
				List<ValidationError> errors = mapError.get(constraintViolation.getPropertyPath().toString()); 
				if(Objects.isNull(errors))
					errors = new ArrayList<ValidationError>();
				
				errors.add(validationError);				
				mapError.put(constraintViolation.getPropertyPath().toString(), errors);
				
				log.info("getPropertyPath() = {} , getMessage {}", constraintViolation.getPropertyPath() , constraintViolation.getMessage());
				constraintViolationMessages.add(constraintViolation.getMessage());							
			}
			
			if(!constraintViolationMessages.isEmpty()) {
				ErrorDetail detailError = new ErrorDetail();
				detailError.setMessage(ErrorCode.DATA_ENTITY_VALIDATOR_ERROR.getMessage());
				detailError.setTimestamp(Calendar.getInstance().getTime());
				detailError.setStatus(HttpStatus.BAD_REQUEST.value());
				detailError.setCode(ErrorCode.DATA_ENTITY_VALIDATOR_ERROR.name());
				//detailError.setPath(VALIDATION_ERROR);				
				detailError.setErrors(mapError);		
								
				throw new FnaException(detailError, HttpStatus.BAD_REQUEST);
			}			
		}
		
	}	
}