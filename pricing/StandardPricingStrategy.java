package pricing;

import java.math.BigDecimal;

public class StandardPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal apply(BigDecimal basePricePerNight) {
        return basePricePerNight;
    }
}
