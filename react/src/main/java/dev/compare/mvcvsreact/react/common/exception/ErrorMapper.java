package dev.compare.mvcvsreact.react.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
@Slf4j
public class ErrorMapper {

    public Mono<ServerResponse> toResponse(Throwable e) {
        log.error(e.getMessage(), e);
        if(e instanceof MvcVsReactException mvcVsReactException) {
            return ServerResponse.status(mvcVsReactException.getHttpStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ErrorResponseDto(e.getMessage()));
        } else {
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ErrorResponseDto(e.getMessage()));
        }
    }
}
