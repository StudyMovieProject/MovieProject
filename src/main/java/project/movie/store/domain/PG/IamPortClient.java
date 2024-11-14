package project.movie.store.domain.PG;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.TokenResponseDto;


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
