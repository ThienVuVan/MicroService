package com.vvt.inventoryservice.service;

import com.vvt.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public Boolean isInStock(String SkuCode){
        return inventoryRepository.findBySkuCode(SkuCode).isPresent();
    }
}
