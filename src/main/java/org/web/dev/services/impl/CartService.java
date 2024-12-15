package org.web.dev.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.OrderContentDTO;
import org.web.dev.services.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final BookService bookService;
    private final ModelMapper modelMapper;


    public CartService(RedisTemplate<String, Object> redisTemplate, BookService bookService, ModelMapper modelMapper) {
        this.redisTemplate = redisTemplate;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    public void addToCart(String username, Long bookId, int quantity) {
        String key = String.format("cart_%s", username);
        Map<Long, Integer> cart = (Map<Long, Integer>) redisTemplate.opsForValue().get(key);

        if (cart == null) {
            cart = new HashMap<>();
        }
        cart.put(bookId, cart.getOrDefault(bookId, 0) + quantity);
        redisTemplate.opsForValue().set(key, cart);
    }

    public List<OrderContentDTO> getCart(String username) {
        String key = String.format("cart_%s", username);
        Map<String, Integer> cart = (Map<String, Integer>) redisTemplate.opsForValue().get(key);
        List<OrderContentDTO> orderContentDTOS = new ArrayList<>();
        if (cart != null && !cart.isEmpty()) {
            Long bookId;
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                bookId = Long.valueOf(entry.getKey());
                BookDTO bookDTO = modelMapper.map(bookService.findById(bookId), BookDTO.class);
                orderContentDTOS.add(new OrderContentDTO(bookDTO, entry.getValue()));
            }
        }
        return orderContentDTOS;
    }

    public void deleteFromCart(String username, Long bookId) {
        String key = String.format("cart_%s", username);
        Map<String, Integer> cart = (Map<String, Integer>) redisTemplate.opsForValue().get(key);
        if (cart != null) {
            cart.remove(String.valueOf(bookId));
            redisTemplate.opsForValue().set(key, cart);
        }
    }

    public void clearCart(String username) {
        String key = String.format("cart_%s", username);
        redisTemplate.delete(key);
    }


}
