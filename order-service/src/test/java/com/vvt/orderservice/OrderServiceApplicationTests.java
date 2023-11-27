package com.vvt.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvt.orderservice.dto.OrderItemsDto;
import com.vvt.orderservice.dto.OrderRequest;
import com.vvt.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderServiceApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrderRepository orderRepository;
    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("123");
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
    @Test
    void shouldPlaceOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        List<OrderItemsDto> orderItemsDtoList = new ArrayList<>();
        OrderItemsDto orderItemsDto = OrderItemsDto.builder()
                .skuCode("iphone_15")
                .price(BigDecimal.valueOf(1200))
                .quantity(5)
                .build();
        orderItemsDtoList.add(orderItemsDto);
        orderRequest.setOrderItemsDtoList(orderItemsDtoList);
        String requestData = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, orderRepository.findAll().size());
    }
}
