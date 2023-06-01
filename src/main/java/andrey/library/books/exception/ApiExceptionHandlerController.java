package andrey.library.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiErrorMessageResponse> handleBookErrors(ApiException apiException) {
        ApiErrorMessageResponse apiErrorMessageResponse =
                new ApiErrorMessageResponse(apiException.getCode(), apiException.getMessage());
        return new ResponseEntity<>(apiErrorMessageResponse, apiException.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiErrorMessageResponse commonHandler(Exception e) {
        return new ApiErrorMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
