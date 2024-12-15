package org.web.dev.controllers;

import org.example.controllers.CartController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.web.dev.services.impl.CartService;

import java.security.Principal;

@Controller
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String addToCart(Principal principal, Long id, Integer quantity) {
        cartService.addToCart(principal.getName(), id, quantity);
        return "redirect:/books/" + id;
    }

    @Override
    public String getCart(Principal principal, Model model) {
        model.addAttribute("cartItems", cartService.getCart(principal.getName()));
        return "main/cart-page.html";
    }

    @Override
    public String clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return "redirect:/cart";
    }

    @Override
    public String removeFromCart(Principal principal, Long id) {
        cartService.deleteFromCart(principal.getName(), id);
        return "redirect:/cart";
    }



}
