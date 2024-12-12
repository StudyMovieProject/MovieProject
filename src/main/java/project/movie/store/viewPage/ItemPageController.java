package project.movie.store.viewPage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequestMapping("/page")
@Controller
@RequiredArgsConstructor
public class ItemPageController {

    private final RestTemplate restTemplate;

    @GetMapping("/items")
    public String getItems(Model model){
        String apiUrl = "http://localhost:8089/api/items";

        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("data");

        model.addAttribute("items",items);
        return "/store/items";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable Integer id, Model model){
        String apiUrl = "http://localhost:8089/api/items/" + id;

        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);
        Map<String, Object> item = (Map<String, Object>) response.getBody().get("data");

        model.addAttribute("item",item);
        return "/store/item-detail";
    }

}
