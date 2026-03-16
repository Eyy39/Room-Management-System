package pricing;

import java.math.BigDecimal;

public class VipServiceFeePricingStrategy implements PricingStrategy {
    private final BigDecimal serviceFee;

    public VipServiceFeePricingStrategy(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public BigDecimal apply(BigDecimal basePricePerNight) {
        return basePricePerNight.add(serviceFee);
    }
}
