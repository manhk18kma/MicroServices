package TTCS.ApiGateWay.repository;

import TTCS.ApiGateWay.repository.DTO.IntrospectRequest;
import TTCS.ApiGateWay.repository.DTO.IntrospectResponse;
import TTCS.ApiGateWay.repository.DTO.ResponseData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseData<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);




}
