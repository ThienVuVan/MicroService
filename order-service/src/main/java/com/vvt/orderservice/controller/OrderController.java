package com.vvt.orderservice.controller;

import com.vvt.orderservice.dto.OrderRequest;
import com.vvt.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackPlaceOrder")
//    @TimeLimiter(name = "inventory", fallbackMethod = "fallBackPlaceOrder")
//    @Retry(name = "inventory", fallbackMethod = "fallBackPlaceOrder")
    public CompletableFuture<ResponseEntity<?>>  placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest))
                .thenApply(isSuccess -> {
                    if (isSuccess) return new ResponseEntity<>("order success", HttpStatus.CREATED);
                    else return new ResponseEntity<>("product is out of stock", HttpStatus.INTERNAL_SERVER_ERROR);
        });
    }
    // runtimeException will consume all exception on father method
    public CompletableFuture<ResponseEntity<?>> fallBackPlaceOrder(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("oop, something went wrong, please try later!",HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
