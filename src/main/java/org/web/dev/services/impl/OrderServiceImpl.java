package org.web.dev.services.impl;

import jakarta.persistence.PersistenceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web.dev.domain.OrderStatus;
import org.web.dev.domain.entities.BookEntity;
import org.web.dev.domain.entities.OrderEntity;
import org.web.dev.domain.entities.UserEntity;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.OrderContentDTO;
import org.web.dev.dtos.OrderDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.BookRepository;
import org.web.dev.repositories.OrderRepository;
import org.web.dev.repositories.UserRepository;
import org.web.dev.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        UserEntity userEntity = userRepository.findById(orderDTO.getUserDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        OrderEntity orderEntity = new OrderEntity(userEntity);
        for (OrderContentDTO orderContentDTO : orderDTO.getOrderContentDTOS()) {
            Long bookId = orderContentDTO.getBookDTO().getId();
            BookEntity bookEntity = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book item with id " + bookId + " not found"));
            orderEntity.setOrderContent(bookEntity, orderContentDTO.getQuantity());
        }
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        OrderDTO savedOrderDTO = convert(savedOrderEntity);
        ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<OrderDTO>> findAllActive() {
        List<OrderDTO> activeOrders = orderRepository.findByStatus(OrderStatus.CREATED).stream()
                .map(this::convert)
                .collect(Collectors.toList());
        ResponseEntity<List<OrderDTO>> responseEntity = ResponseEntity.ok(activeOrders);
        return responseEntity;
    }

    @Override
    public ResponseEntity<OrderDTO> findById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        OrderDTO savedOrderDTO = convert(orderEntity);
        ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<OrderDTO> updateOrderStatus(Long id, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        orderEntity.changeStatus(orderStatus);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        OrderDTO savedOrderDTO = convert(savedOrderEntity);
        ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
        return responseEntity;

    }


    private OrderDTO convert(OrderEntity orderEntity) {
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
        List<OrderContentDTO> orderContentDTOs = orderEntity.getOrderContentEntities().stream()
                .map(orderContentEntity -> {
                    OrderContentDTO orderContentDTO = modelMapper.map(orderContentEntity, OrderContentDTO.class);
                    BookDTO bookDTO = modelMapper.map(orderContentEntity.getBookEntity(), BookDTO.class);
                    orderContentDTO.setBookDTO(bookDTO);
                    return orderContentDTO;
                })
                .collect(Collectors.toList());

        orderDTO.setOrderContentDTOS(orderContentDTOs);
        return orderDTO;
    }

}
