package com.springjwt.boilerplate.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.springjwt.boilerplate.model.AppResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

	@Context
	private HttpHeaders httpHeaders;
	
	@Override
	public Response toResponse(Exception exception) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		AppResponse responseMap = new AppResponse();
	    responseMap.setName(exception.getClass().getSimpleName());
	    responseMap.setMessage(exception.getMessage());

	    switch (exception.getClass().getSimpleName()) {
	    case "HttpClientErrorException":
	      HttpClientErrorException clientError = (HttpClientErrorException) exception;
	      if (clientError.getStatusCode().value() == 401) {
	        responseMap.setName("Request is unauthorized");
	        builder.status(Status.UNAUTHORIZED);
	        builder.entity(responseMap);
	        builder.type("application/json");
	        return builder.build();
	      }
	      else if (clientError.getStatusCode().value() == 404) {
	        responseMap.setName("Not found");
	        builder.status(Status.NOT_FOUND);
	        builder.entity(responseMap);
	        builder.type("application/json");
	        return builder.build();
	      }
	      else if (clientError.getStatusCode().value() == 400) {
	        responseMap.setName("Bad Request");
	        responseMap.setDescription(clientError.getResponseBodyAsString());
	        builder.status(Status.BAD_REQUEST);
	        builder.entity(responseMap);
	        builder.type("application/json");
	        return builder.build();
	      }
	      else {
	        return builder.entity(responseMap).status(Status.INTERNAL_SERVER_ERROR)
	            .type(httpHeaders.getMediaType()).build();
	      }
	    case "DefaultOptionsMethodException":
	      return Response.ok().build();
	    case "IllegalArgumentException":
	    case "BadRequestException":
	    	builder.status(Status.BAD_REQUEST);
	      builder.entity(responseMap);
	      builder.type("application/json");
	      return builder.build();
	    case "AccessDeniedException":
	      return builder.entity(responseMap).status(Status.UNAUTHORIZED)
	          .type("application/json").build();
	    case "InvalidCacheLoadException":
	      responseMap.setName("NotFoundException");
	      return builder.entity(responseMap).status(Status.NOT_FOUND).type("application/json")
	          .build();
	    case "NotFoundException":
	      return builder.entity(responseMap).status(Status.NOT_FOUND).type("application/json")
	          .build();
	    case "NotAuthorizedException":
	      return builder.entity(responseMap).status(Status.UNAUTHORIZED)
	          .type("application/json").build();
	    default:
	      log.error("Error occured,", exception);
	      return builder.entity(responseMap).status(Status.INTERNAL_SERVER_ERROR)
	          .type(httpHeaders.getMediaType()).build();
	    }
	  }

}


