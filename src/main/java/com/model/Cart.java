package com.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, CartItem> items;

    public Cart() {
        items = new HashMap<>();
    }

    // Add Item
    public void addItem(CartItem item) {
        int id = item.getId();

        if (items.containsKey(id)) {
            CartItem existingItem = items.get(id);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(id, item);
        }
    }

    // Update Quantity
    public void updateItem(int id, int quantity) {
        if (items.containsKey(id)) {
            if (quantity <= 0) {
                items.remove(id);
            } else {
                items.get(id).setQuantity(quantity);
            }
        }
    }

    // Remove Item
    public void removeItem(int id) {
        items.remove(id);
    }

    // Get Items
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    // Total Price
    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }
}