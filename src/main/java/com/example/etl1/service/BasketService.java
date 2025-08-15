package com.example.etl1.service;

import com.example.etl1.dto.BasketResponseDto;
import com.example.etl1.model.Basket;
import com.example.etl1.model.BasketItem;
import com.example.etl1.model.Product;  // Changed from entity to model
import com.example.etl1.repository.BasketRepository;
import com.example.etl1.repository.BasketItemRepository;
import com.example.etl1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional  // Ensures database consistency
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Basket getOrCreateBasket(String sessionId) {
        Optional<Basket> existingBasket = basketRepository.findBySessionId(sessionId);
        if (existingBasket.isPresent()) {
            return existingBasket.get();
        }

        // Create new basket if none exists
        Basket newBasket = new Basket(sessionId);
        return basketRepository.save(newBasket);
    }

    public BasketResponseDto addItemToBasket(String sessionId, Integer productId, Integer quantity) {  // Changed Long to Integer
        // Get or create basket for this session
        Basket basket = getOrCreateBasket(sessionId);

        // Find the product (throw exception if not found)
        Product product = productRepository.findById(productId)  // Now works with Integer
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Check if this product is already in the basket
        Optional<BasketItem> existingItem = basketItemRepository.findByBasketAndProduct(basket, product);

        if (existingItem.isPresent()) {
            // Product already in basket - update quantity
            BasketItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            basketItemRepository.save(item);
        } else {
            // New product - create new basket item
            BasketItem newItem = new BasketItem(basket, product, quantity);
            basketItemRepository.save(newItem);
            basket.getItems().add(newItem);
        }

        // Save basket and return as DTO
        basket = basketRepository.save(basket);
        return convertToResponseDto(basket);
    }

    public BasketResponseDto updateItemQuantity(String sessionId, Integer productId, Integer quantity) {  // Changed Long to Integer
        Basket basket = getOrCreateBasket(sessionId);
        Product product = productRepository.findById(productId)  // Now works with Integer
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Optional<BasketItem> existingItem = basketItemRepository.findByBasketAndProduct(basket, product);

        if (existingItem.isPresent()) {
            if (quantity <= 0) {
                // Remove item if quantity is 0 or negative
                basketItemRepository.delete(existingItem.get());
                basket.getItems().remove(existingItem.get());
            } else {
                // Update quantity
                BasketItem item = existingItem.get();
                item.setQuantity(quantity);
                basketItemRepository.save(item);
            }
        } else {
            throw new RuntimeException("Item not found in basket");
        }

        basket = basketRepository.save(basket);
        return convertToResponseDto(basket);
    }

    /**
     * Removes an item completely from the basket
     */
    public BasketResponseDto removeItemFromBasket(String sessionId, Integer productId) {  // Changed Long to Integer
        Basket basket = getOrCreateBasket(sessionId);
        Product product = productRepository.findById(productId)  // Now works with Integer
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Optional<BasketItem> existingItem = basketItemRepository.findByBasketAndProduct(basket, product);

        if (existingItem.isPresent()) {
            basketItemRepository.delete(existingItem.get());
            basket.getItems().remove(existingItem.get());
            basket = basketRepository.save(basket);
        }

        return convertToResponseDto(basket);
    }

    public BasketResponseDto getBasket(String sessionId) {
        Basket basket = getOrCreateBasket(sessionId);
        return convertToResponseDto(basket);
    }

    @Transactional
    public void clearBasket(Basket basket) {
        basket.getItems().clear();
        basketRepository.save(basket);
    }

    /**
     * Converts internal Basket entity to external DTO with calculated totals
     */
    private BasketResponseDto convertToResponseDto(Basket basket) {
        BasketResponseDto dto = new BasketResponseDto();
        dto.setBasketId(basket.getId());
        dto.setSessionId(basket.getSessionId());

        // Convert each basket item to DTO
        List<BasketResponseDto.BasketItemDto> itemDtos = basket.getItems().stream()
                .map(this::convertToItemDto)
                .collect(Collectors.toList());

        dto.setItems(itemDtos);

        // Calculate total price for the entire basket
        BigDecimal totalPrice = basket.getItems().stream()
                .map(BasketItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalPrice(totalPrice);

        // Calculate total number of items (sum of all quantities)
        Integer totalItems = basket.getItems().stream()
                .mapToInt(BasketItem::getQuantity)
                .sum();
        dto.setTotalItems(totalItems);

        return dto;
    }

    /**
     * Converts a BasketItem entity to a BasketItemDto
     */
    private BasketResponseDto.BasketItemDto convertToItemDto(BasketItem item) {
        BasketResponseDto.BasketItemDto dto = new BasketResponseDto.BasketItemDto();
        dto.setItemId(item.getId());
        dto.setProductId(item.getProduct().getId().longValue());  // Convert Integer to Long for DTO
        dto.setProductName(item.getProduct().getName());
        dto.setProductPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setTotalPrice(item.getTotalPrice()); // This uses the method from BasketItem entity
        return dto;
    }
}