package br.edu.utfpr.pb.pw44s.server.service.impl;

import br.edu.utfpr.pb.pw44s.server.dto.OrderDTO;
import br.edu.utfpr.pb.pw44s.server.dto.OrderItemDTO;
import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.OrderItems;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.OrderItemsRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderRepository;
import br.edu.utfpr.pb.pw44s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemsRepository orderItemsRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO, String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        Order order = Order.builder()
                .data(LocalDateTime.now())
                .userId(user.getId())
                .build();
        order = orderRepository.save(order);

        List<OrderItemDTO> savedItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (orderDTO.getItems() != null) {
            for (OrderItemDTO itemDTO : orderDTO.getItems()) {
                Product product = productRepository.findById(itemDTO.getProductId()).orElse(null);
                BigDecimal price = itemDTO.getPrice();
                if (price == null && product != null) {
                    price = product.getPrice();
                } else if (price == null) {
                    price = BigDecimal.ZERO;
                }

                String productName = product != null ? product.getName() : itemDTO.getProductName();

                OrderItems item = OrderItems.builder()
                        .id(OrderItems.ItensDoPedidoId.builder()
                                .orderId(order.getId())
                                .productId(itemDTO.getProductId())
                                .build())
                        .price(price)
                        .quantity(itemDTO.getQuantity())
                        .build();

                orderItemsRepository.save(item);

                BigDecimal subtotal = price.multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
                total = total.add(subtotal);

                savedItems.add(OrderItemDTO.builder()
                        .productId(itemDTO.getProductId())
                        .productName(productName)
                        .price(price)
                        .quantity(itemDTO.getQuantity())
                        .build());
            }
        }

        return OrderDTO.builder()
                .id(order.getId())
                .data(order.getData())
                .userId(order.getUserId())
                .items(savedItems)
                .total(total)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findOrdersByUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return Collections.emptyList();
        }

        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            orderDTOs.add(buildOrderDTO(order));
        }

        return orderDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id, String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return null;
        }

        Order order = orderRepository.findById(id).orElse(null);
        if (order == null || !order.getUserId().equals(user.getId())) {
            return null;
        }

        return buildOrderDTO(order);
    }

    private OrderDTO buildOrderDTO(Order order) {
        List<OrderItems> items = orderItemsRepository.findByIdOrderId(order.getId());
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItems item : items) {
            Product product = productRepository.findById(item.getId().getProductId()).orElse(null);
            String productName = product != null ? product.getName() : null;

            BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);

            itemDTOs.add(OrderItemDTO.builder()
                    .productId(item.getId().getProductId())
                    .productName(productName)
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build());
        }

        return OrderDTO.builder()
                .id(order.getId())
                .data(order.getData())
                .userId(order.getUserId())
                .items(itemDTOs)
                .total(total)
                .build();
    }
}
