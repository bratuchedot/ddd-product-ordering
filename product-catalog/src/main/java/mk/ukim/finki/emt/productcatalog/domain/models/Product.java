package mk.ukim.finki.emt.productcatalog.domain.models;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import mk.ukim.finki.emt.productcatalog.domain.valueobjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
public class Product extends AbstractEntity<ProductId> {

    private String productName;

    private int sales = 0;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;

    private Product() {
        super(ProductId.randomId(ProductId.class));
    }

    public static Product build(String productName, Money price, int sales) {
        Product product = new Product();
        product.productName = productName;
        product.price = price;
        product.sales = sales;
        return product;
    }

    public void addSales(int qty) {
        this.sales = this.sales + qty;
    }

    public void removeSales(int qty) {
        this.sales = this.sales - qty;
    }
}
