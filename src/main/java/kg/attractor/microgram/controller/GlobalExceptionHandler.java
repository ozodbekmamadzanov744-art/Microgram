package kg.attractor.microgram.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.microgram.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound(NotFoundException exception, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage(), exception, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView forbidden(AccessDeniedException exception, HttpServletRequest request) {
        return error(HttpStatus.FORBIDDEN, "Нет доступа к этому действию", exception, request);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ModelAndView badRequest(Exception exception, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Некорректные данные запроса", exception, request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView largeFile(Exception exception, HttpServletRequest request) {
        return error(HttpStatus.PAYLOAD_TOO_LARGE, "Размер файла не должен превышать 5 МБ", exception, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView conflict(Exception exception, HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, "Данные уже изменились. Обновите страницу", exception, request);
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView fileError(Exception exception, HttpServletRequest request) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось обработать файл", exception, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView unexpectedError(RuntimeException exception, HttpServletRequest request) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка приложения", exception, request);
    }

    private ModelAndView error(HttpStatus status, String message, Exception exception,
                               HttpServletRequest request) {
        String login = request.getUserPrincipal() == null ? "гость" : request.getUserPrincipal().getName();
        if (status.is5xxServerError()) {
            log.error("Пользователь {}, запрос {} {}", login, request.getMethod(), request.getRequestURI(), exception);
        } else {
            log.warn("Пользователь {}, запрос {} {}, статус {}", login, request.getMethod(),
                    request.getRequestURI(), status.value());
        }
        ModelAndView view = new ModelAndView("error/message");
        view.setStatus(status);
        view.addObject("message", message);
        view.addObject("status", status.value());
        return view;
    }
}
