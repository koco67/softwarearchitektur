package com.htw.basket.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BasketDTO {
        private String cartID;
        private List<ProductDTO> items = new ArrayList<>();
        private BigDecimal total;
        public CartDTO(String cartID) {
            this.cartID=cartID;

        }

        public void setItems(List<ProductDTO> items) {
            this.items = items;
            calculateTotal();
            countTotalQuantity();
        }

        public void countTotalQuantity(){
            int totalQuantity=0;
            for (ProductDTO product: items) {
                totalQuantity+=product.getQuantity();
            }
            this.quantity=totalQuantity;
        }

        public void calculateTotal (){
            BigDecimal subTotal = new BigDecimal("0.0");
            for (ProductDTO product: items) {
                BigDecimal price = product.getProductSalesPrice()!= null ? product.getProductSalesPrice() : product.getProductPrice();
                subTotal =  subTotal.add(price.multiply(BigDecimal.valueOf(product.getQuantity())));
            }
            this.subTotal = subTotal;
            this.shipping = subTotal.compareTo(shppingFreeLimit) >= 0 ? new BigDecimal("0.0") : shipping;

            this.total = subTotal.add(this.shipping);
        }

        @Override
        public String toString() {
            return "CartDTO{" +
                    "cartID='" + cartID + '\'' +
                    ", items=" +items.size() +
                    '}';
        }
    }
