package co.gov.fna.core.common.exception;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
 
/**
 * The Class RestExceptionHandler.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> Creation date 24/05/2018
 * @version 1.5
 * @since 1.0
 */
@ControllerAdvice
/** The Constant log. */
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/** The message source. */
	@Autowired
	public MessageSource messageSource;
	
	/** The Constant SEPARATOR. */
	public static final String SEPARATOR = ".";
					
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {		
				
		HttpServletRequest httpServletRequest = ((ServletWebRequest)request).getRequest();
		
		final ErrorDetail detailError = new ErrorDetail();
		detailError.setMessage(ErrorCode.FIND_NOT_FOUND_ERROR.getMessage());
		detailError.setTimestamp(Calendar.getInstance().getTime());
		detailError.setStatus(status.value());
		detailError.setCode(exception.getMessage());
		detailError.setPath(httpServletRequest.getRequestURI());
					
		return handleExceptionInternal(exception , detailError , headers , status , request);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		HttpServletRequest httpServletRequest = ((ServletWebRequest)request).getRequest();
			
		final ErrorDetail detailError = new ErrorDetail();
		detailError.setMessage(ErrorCode.DATA_ENTITY_VALIDATOR_ERROR.getMessage());
		detailError.setTimestamp(Calendar.getInstance().getTime());
		detailError.setStatus(status.value());
		detailError.setCode(ErrorCode.DATA_ENTITY_VALIDATOR_ERROR.name());
		log.info("Error Code: {} Exception: {}", detailError.getCode(), exception.getMessage());
		detailError.setPath(httpServletRequest.getRequestURI());

		getFieldErrors(exception, detailError);
		
		return handleExceptionInternal(exception, detailError, headers, status, request);
	}

	/**
	 * Gets the field errors.
	 *
	 * @param exception the exception
	 * @param detailError the detail error
	 * @return the field errors
	 */
	private void getFieldErrors(final MethodArgumentNotValidException exception, final ErrorDetail detailError) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		fieldErrors.forEach(fe -> {

			List<ValidationError> validationErrorList = detailError.getErrors().get(fe.getField());
			if (Objects.isNull(validationErrorList)) {
				validationErrorList = new ArrayList<ValidationError>();
				detailError.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = ValidationError.builder().build();
			validationError.setCode(fe.getCode());			
			validationError.setMessage(fe.getDefaultMessage());
			validationErrorList.add(validationError);
		});
	}	
	
	/**
	 * Handle service exception.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(FnaException.class)
	public final ResponseEntity<Object> handleServiceException(final FnaException fnaException, final WebRequest request) {
		
	
		HttpServletRequest httpServletRequest = ((ServletWebRequest)request).getRequest();
		fnaException.getErrorDetail().setPath(httpServletRequest.getRequestURI());
		
		return new ResponseEntity<>(fnaException.getErrorDetail(), fnaException.getStatus());
	}
}
