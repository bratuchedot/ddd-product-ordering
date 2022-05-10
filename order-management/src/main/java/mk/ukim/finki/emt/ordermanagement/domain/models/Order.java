package mk.ukim.finki.emt.ordermanagement.domain.models;

import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity<OrderId> {

    private Instant orderedOn;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @Embedded
    private Money total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemList;

    public Order() {

    }

    public Money getTotal() {
        return total;
    }
}
