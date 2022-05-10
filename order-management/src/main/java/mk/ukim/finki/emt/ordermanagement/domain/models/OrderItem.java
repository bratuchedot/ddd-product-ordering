package mk.ukim.finki.emt.ordermanagement.domain.models;

import mk.ukim.finki.emt.ordermanagement.valueobjects.ProductId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity<OrderItemId> {

    @Embedded
    private Money itemPrice;

    @Column(name = "qty", nullable = false)
    private int quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;

    public Money getItemPrice() {
        return itemPrice;
    }

    public ProductId getProductId() {
        return productId;
    }

}
