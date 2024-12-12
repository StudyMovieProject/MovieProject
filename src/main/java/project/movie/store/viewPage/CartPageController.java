package project.movie.store.viewPage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartPageController {

    private final RestTemplate restTemplate;

    @GetMapping("/page/cart")
    public String getCartPage(Model model) {
        String apiUrl = "http://localhost:8089/api/cart";
        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            model.addAttribute("cartData", response.getBody().get("data"));
            model.addAttribute("msg", response.getBody().get("msg"));
        } else {
            model.addAttribute("msg", "장바구니 정보를 불러올 수 없습니다.");
            model.addAttribute("cartData", null);
        }

        return "store/cart";
    }

}
