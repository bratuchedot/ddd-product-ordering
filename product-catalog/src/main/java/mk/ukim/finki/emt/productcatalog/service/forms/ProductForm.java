package mk.ukim.finki.emt.productcatalog.service.forms;

import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Data
public class ProductForm {

    private String productName;

    private Money price;

    private int sales;
}
