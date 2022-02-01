package edu.miu.cs544.identityprovider.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    //TODO: - Check with Not found error message complaint
    @ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundDTO processNotFoundError(HttpServletRequest request, Exception e) {
        NotFoundDTO dto = new NotFoundDTO();
        dto.setTimestamp(new Date());
        dto.setError("Not found");
        dto.setMessage("Request with the specified id Not found");
        dto.setName("id");
        dto.setPath(request.getRequestURI());
        return dto;
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO("ValidationError");
        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(fieldError.getField(), messageSourceAccessor.getMessage(fieldError));
        }
        return dto;
    }

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public NotFoundDTO handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new NotFoundDTO(
                "Refresh Token",
                "Token not Found",
                ex.getMessage(),
                ""
        );
    }
}
