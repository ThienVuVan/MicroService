package com.vvt.orderservice.controller;

import com.vvt.orderservice.dto.OrderRequest;
import com.vvt.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<?> placeOrder(OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
