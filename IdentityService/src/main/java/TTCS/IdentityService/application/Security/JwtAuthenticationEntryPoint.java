package TTCS.IdentityService.application.Security;

import TTCS.IdentityService.application.Exception.AppException.AppErrorCode;
import TTCS.IdentityService.presentation.command.dto.response.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(
//            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//            throws IOException, ServletException {
//        AppErrorCode errorCode = AppErrorCode.UNAUTHENTICATED;
//        response.setStatus(errorCode.getStatusCode().value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        ResponseData<?> responseData = ResponseData.builder()
//                .status(errorCode.getCode())
//                .timestamp(new Date())
//                .message(errorCode.getMessage()).build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        response.getWriter().write(objectMapper.writeValueAsString(responseData));
//        response.flushBuffer();
//    }
@Override
public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {
    System.out.println("here");
    AppErrorCode errorCode = AppErrorCode.UNAUTHENTICATED;
    response.setStatus(errorCode.getStatusCode().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    // Sử dụng Date để tạo timestamp
    Date timestamp = new Date();

    ResponseData<?> responseData = ResponseData.builder()
            .status(errorCode.getCode())
            .timestamp(timestamp)
            .message(errorCode.getMessage())
            .build();

    System.out.println(responseData.toString());

    ObjectMapper objectMapper = new ObjectMapper();
    response.getWriter().write(objectMapper.writeValueAsString(responseData));
    response.flushBuffer();
}

}
