package mk.ukim.finki.emt.productcatalog.domain.models;

import mk.ukim.finki.emt.productcatalog.domain.valueobjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity<ProductId> {

    private String productName;

    @Embedded
    private Quantity quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;

    public Money getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

}
