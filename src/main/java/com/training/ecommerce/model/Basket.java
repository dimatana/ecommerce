package com.training.ecommerce.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BasketItem> items = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<BasketItem> getItems() {
        return items;
    }

    public void setItems(Set<BasketItem> items) {
        this.items = items;
    }
}
