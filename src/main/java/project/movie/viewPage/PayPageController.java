package project.movie.viewPage;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.movie.store.domain.cart.Cart;
import project.movie.store.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/page/pay")
public class PayPageController {

    private final CartService cartService;

    @PostMapping
    public String showPaymentPage(@RequestParam("selectedItems") String selectedItems,
                                  Model model) {
        // selectedItems에서 마지막 [] 안의 부분만 추출
        String cartCodeJson = selectedItems.substring(selectedItems.indexOf("[") + 1, selectedItems.lastIndexOf("]"));
        String[] cartCodes = cartCodeJson.replace("\"", "").split(",");

        List<Cart> carts = new ArrayList<>();
        Integer calculatedTotalPrice = 0;


        for (String cartCode : cartCodes) {
            System.out.println(cartCode);
            Cart cart = cartService.findByCartCode(Integer.parseInt(cartCode));
            if (cart != null) {
                carts.add(cart);
                calculatedTotalPrice += (cart.getItem().getPrice() - cart.getItem().getSalePrice()) * cart.getCartQty();
            }
        }

        List<Integer> cartCodes_ = carts.stream()
                .map(Cart::getCartCode)
                .collect(Collectors.toList());

        model.addAttribute("cartItems",carts);
        model.addAttribute("totalPrice", calculatedTotalPrice);
        model.addAttribute("cartCodes", cartCodes_);

        return "store/before-pay";  // payment.html page
    }
}
