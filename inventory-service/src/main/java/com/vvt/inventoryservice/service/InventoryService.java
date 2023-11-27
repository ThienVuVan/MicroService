package com.vvt.inventoryservice.service;

import com.vvt.inventoryservice.dto.InventoryResponse;
import com.vvt.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public List<InventoryResponse> isInStock(List<String> SkuCode){
        return inventoryRepository.findBySkuCodeIn(SkuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .SkuCode(inventory.getSkuCode())
                        .IsInStocks(inventory.getQuantity() > 0)
                        .build()).collect(Collectors.toList());
    }
}
