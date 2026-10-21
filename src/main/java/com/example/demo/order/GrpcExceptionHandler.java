package com.example.demo.order;

import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GrpcExceptionHandler {

	@ExceptionHandler(StatusRuntimeException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	ErrorResponse handleGrpcError(StatusRuntimeException exception) {
		return new ErrorResponse("orderService недоступен", exception.getStatus().getCode().name());
	}

	record ErrorResponse(String message, String grpcStatus) {
	}
}