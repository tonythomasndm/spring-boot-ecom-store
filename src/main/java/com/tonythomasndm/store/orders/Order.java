package com.tonythomasndm.store.orders;

import com.tonythomasndm.store.carts.Cart;
import com.tonythomasndm.store.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)// dont use oridnal bcoz will affct // we cna use it if we normalize it
    private PaymentStatus status;


    @Column(name = "created_at", insertable = false, updatable = false)// with this we inform dont casre abt it, db will qassign the value
    private LocalDateTime createdAt;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<OrderItem> items = new LinkedHashSet<>();

    public static Order fromCart(Cart cart, User customer){
        var order = new Order();
        order.setCustomer(customer);
        order.setStatus(PaymentStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getItems().forEach(cartItem -> {
            var orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity());
            order.items.add(orderItem);
        });
        return order;
    }

    public boolean isPlacedBy(User customer){
        return this.customer.equals(customer);
    }

}