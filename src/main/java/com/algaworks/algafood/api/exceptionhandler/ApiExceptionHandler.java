package com.algaworks.algafood.api.exceptionhandler;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.exceptionhandler.Field;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado do sistema, "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";
	
	private static final String MSG_ACESSO_NEGADO = "Você não possui permissão para executar essa operação.";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
//	ValidacaoException
	@ExceptionHandler({ValidacaoException.class})
	public ResponseEntity<Object> handleValidacao(ValidacaoException ex, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
//	MethodArgumentNotValidException
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);			
	}
	
	
	public ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.";
		String userMessage = detail;
		
		List<Field> problemFields = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name =  objectError.getObjectName();
					
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					return createFieldBuilder(name, message);
//					return createField(name, message);
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblem(status, problemType, detail, userMessage);
		problem.setFields(problemFields);
		return handleExceptionInternal(ex, problem, headers, status, request);
				
		/*
		List<Field> problemFields = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new Field(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
			
		Problem problem = createProblem(status, problemType, detail, userMessage);
		problem.setFields(problemFields);
		*/
	}

	private Field createFieldBuilder(String name, String message) {
		// TODO Auto-generated method stub
		return Field.builder()
				.name(name)
				.userMessage(message)
				.build();
	}
	
	private Field createField( String name, String message) {
		Field objects = new Field(name, message);
	return objects;
	}
	
	/*
	private Field createField(FieldError fieldError) {
		Field fields = new Field();
		fields.setName(fieldError.getField());
		fields.setUserMessage(fieldError.getDefaultMessage());
	return fields;	
	}
	*/

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		String userMessage = MSG_ERRO_GENERICA_USUARIO_FINAL;
		
		Problem problem = createProblem(status, problemType, detail, userMessage);
//		ex.printStackTrace();
		log.error(ex.getMessage(), ex);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String path = ex.getRequestURL();
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", path);
		String userMessage = MSG_ERRO_GENERICA_USUARIO_FINAL;
		
		Problem problem = createProblem(status, problemType, detail, userMessage);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		//IgnoredPropertyException -> PropertyBindingException
		//UnrecognizedPropertyException -> PropertyBindingException
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException)rootCause, headers, status, request);
		
		} else if(rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredProperty((IgnoredPropertyException)rootCause, headers, status, request);
		
		} else if(rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException)rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido, verifique erro de sintaxe.";
		String userMessage = "O corpo da requisição está inválido, verifique erro de sintaxe.";
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		//String path = ex.getPropertyName();
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' não existe. Verifique erro de sintaxe", path);
		String userMessage = "O corpo da requisição está inválido, verifique erro de sintaxe.";
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleIgnoredProperty(IgnoredPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' existe, porém está sendo ignorada no momento da serialização/ desserialização."
				+ " No momento, essa propriedade não pode ser atribuida.", path);
		String userMessage = "O corpo da requisição está inválido, verifique erro de sintaxe.";
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s"
				, path, ex.getValue(), ex.getTargetType().getSimpleName());
		String userMessage = MSG_ERRO_GENERICA_USUARIO_FINAL;
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'"
				, ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		String userMessage = MSG_ERRO_GENERICA_USUARIO_FINAL;
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		
//		Problem problema = Problem.builder().dataHora(LocalDateTime.now()).mensagem(ex.getMessage()).build();
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		String userMessage = detail;
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		String userMessage = detail;
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
				
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		String userMessage = detail;
		Problem problem = createProblem(status, problemType, detail, userMessage);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;
		String detail = ex.getMessage();
		String userMessage = MSG_ACESSO_NEGADO;
		Problem problem = createProblem(status, problemType, detail, userMessage);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = new Problem();
			((Problem) body).setTitle(status.getReasonPhrase());
			((Problem) body).setStatus(status.value());
			((Problem)body).setTimestamp(OffsetDateTime.now());
			((Problem)body).setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		} else if(body instanceof String) {
			body = new Problem();	
			((Problem) body).setTitle(ex.getMessage());
			((Problem) body).setStatus(status.value());
			((Problem)body).setTimestamp(OffsetDateTime.now());
			((Problem)body).setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String joinPath(List<Reference> path) {
		
		return path.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}
	
	private Problem createProblem(HttpStatus status, ProblemType problemType, String detail, String userMessage) {
		
		Problem novoProblema = new Problem();
		novoProblema.setDetail(detail);
		novoProblema.setStatus(status.value());
		novoProblema.setType(problemType.getUri());
		novoProblema.setTitle(problemType.getTitle());
		novoProblema.setTimestamp(OffsetDateTime.now());
		novoProblema.setUserMessage(userMessage);
		return novoProblema;
	}
}
