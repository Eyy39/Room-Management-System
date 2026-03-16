package pricing;

import java.math.BigDecimal;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    @Override
    public BigDecimal calculateDiscount(BigDecimal subtotal, BigDecimal discountPercent) {
        if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal percent = discountPercent.min(HUNDRED);
        return subtotal.multiply(percent).divide(HUNDRED);
    }
}
