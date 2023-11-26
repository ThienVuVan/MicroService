package com.vvt.orderservice.service;

import com.vvt.orderservice.dto.OrderItemsDto;
import com.vvt.orderservice.dto.OrderRequest;
import com.vvt.orderservice.model.Order;
import com.vvt.orderservice.model.OrderItems;
import com.vvt.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItemsList = orderRequest.getOrderItemsDtoList()
                .stream()
                .map(orderItemsDto -> mapToOrderItems(orderItemsDto))
                .collect(Collectors.toList());
        order.setOrderItemsList(orderItemsList);
        orderRepository.save(order);
    }

    private OrderItems mapToOrderItems(OrderItemsDto orderItemsDto) {
        return OrderItems.builder()
                .price(orderItemsDto.getPrice())
                .quantity(orderItemsDto.getQuantity())
                .skuCode(orderItemsDto.getSkuCode()).build();
    }
}
