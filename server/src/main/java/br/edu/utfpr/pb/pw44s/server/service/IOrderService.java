package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO createOrder(OrderDTO orderDTO, String username);
    List<OrderDTO> findOrdersByUser(String username);
    OrderDTO findOrderById(Long id, String username);
}
