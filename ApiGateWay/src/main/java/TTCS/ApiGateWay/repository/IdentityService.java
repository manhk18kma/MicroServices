package TTCS.ApiGateWay.repository;

import TTCS.ApiGateWay.repository.DTO.IntrospectRequest;
import TTCS.ApiGateWay.repository.DTO.IntrospectResponse;
import TTCS.ApiGateWay.repository.DTO.ResponseData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ResponseData<IntrospectResponse>> introspect(String token){
//        processIntrospectRequest(token);
        return identityClient.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());
    }
//    public void processIntrospectRequest(String token) {
//        identityClient.introspect(IntrospectRequest.builder()
//                        .token(token)
//                        .build())
//                .subscribe(responseData -> {
//                    // Xử lý giá trị responseData ở đây
//                    System.out.println(responseData); // In ra giá trị của responseData
//                });
//    }














}
