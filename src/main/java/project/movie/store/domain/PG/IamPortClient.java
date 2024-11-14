package project.movie.store.domain.PG;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.TokenResponseDto;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class IamPortClient {
    @Value("${iamport.apiKey}")
    private String apiKey;
    @Value("${iamport.apiSecret}")
    private String apiSecret;
    @Value("${iamport.info}")
    private String impUid;

    private static final String BASE_URL = "https://api.iamport.kr/";

    private final RestTemplate restTemplate;

    public String getImpPortUid(){
        return impUid;
    }

    public IamportResponseDto paymentByImpUid(String impUid){
        String accessToken = getAccessToken();
        String url = BASE_URL + "payment/" + impUid;

        ResponseEntity<IamportResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(accessToken)),
                IamportResponseDto.class
        );
        return response.getBody();
    }


    public IamportResponseDto cancelPaymentByImpUid(String impUid, String reason) {

        String cancelUrl = BASE_URL + "/payments/cancel";

        HttpHeaders headers = createHeaders(getAccessToken());
        headers.set("Content-Type", "application/json");

        Map<String, Object> cancelData = new HashMap<>();
        cancelData.put("imp_uid", impUid);
        cancelData.put("reason", reason);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(cancelData, headers);

        try {
            ResponseEntity<IamportResponseDto> response = restTemplate.exchange(
                    cancelUrl,
                    HttpMethod.POST,
                    requestEntity,
                    IamportResponseDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new CustomApiException("결제 취소 실패: Iamport 응답 오류");
            }

        } catch (Exception e) {
            throw new CustomApiException("결제 취소 요청 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    private String getAccessToken(){
        String tokenUrl = BASE_URL + "users/getToken";
        String body = String.format("{\"imp_key\": \"%s\" , \"imp_secret\" : \"%s\"}", apiKey,apiSecret);

        ResponseEntity<TokenResponseDto> tokenResponse = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                new HttpEntity<>(body, createHeaders(null)),
                TokenResponseDto.class
        );

        return tokenResponse.getBody().getAccessToken();
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        if (accessToken != null) {
            headers.set("Authorization", accessToken);
        }
        return headers;
    }
}
