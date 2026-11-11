package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.OrderDTO;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO, Principal principal) {
        OrderDTO created = orderService.createOrder(orderDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findOrders(Principal principal) {
        List<OrderDTO> orders = orderService.findOrdersByUser(principal.getName());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id, Principal principal) {
        OrderDTO order = orderService.findOrderById(id, principal.getName());
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
