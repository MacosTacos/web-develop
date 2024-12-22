package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.CartController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.web.dev.services.impl.CartService;

import java.security.Principal;

@Controller
public class CartControllerImpl implements CartController {

    private final CartService cartService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String addToCart(Principal principal, Long id, Integer quantity) {
        LOG.info("POST:/cart/add Add to cart request from " + principal.getName());
        cartService.addToCart(principal.getName(), id, quantity);
        return "redirect:/books/" + id;
    }

    @Override
    public String getCart(Principal principal, Model model) {
        LOG.info("GET:/cart Show cart request from " + principal.getName());
        model.addAttribute("cartItems", cartService.getCart(principal.getName()));
        return "main/cart-page.html";
    }

    @Override
    public String clearCart(Principal principal) {
        LOG.info("POST:/cart/clear Cart emptied by user " + principal.getName());
        cartService.clearCart(principal.getName());
        return "redirect:/cart";
    }

    @Override
    public String removeFromCart(Principal principal, Long id) {
        LOG.info("POST:/cart/remove Remove from cart request from " + principal.getName());
        cartService.deleteFromCart(principal.getName(), id);
        return "redirect:/cart";
    }



}
