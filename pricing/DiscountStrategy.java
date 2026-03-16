package pricing;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(BigDecimal subtotal, BigDecimal discountInput);
}
