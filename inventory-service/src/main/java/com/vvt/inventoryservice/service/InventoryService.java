package com.vvt.inventoryservice.service;

import com.vvt.inventoryservice.dto.InventoryResponse;
import com.vvt.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
//    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> SkuCode){
//        log.info("start");
//        Thread.sleep(10000);
//        log.info("end");
        return inventoryRepository.findBySkuCodeIn(SkuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .SkuCode(inventory.getSkuCode())
                        .IsInStocks(inventory.getQuantity() > 0)
                        .build()).collect(Collectors.toList());
    }
}
