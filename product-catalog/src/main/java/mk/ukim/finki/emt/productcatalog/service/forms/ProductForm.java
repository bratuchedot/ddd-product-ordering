package mk.ukim.finki.emt.productcatalog.service.forms;

import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.validation.constraints.NotNull;

@Data
public class ProductForm {

    @NotNull
    private String productName;

    @NotNull
    private Money price;

    @NotNull
    private int sales;
}
