package pricing;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal apply(BigDecimal basePricePerNight);
}
