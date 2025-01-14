import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.coreapi.ventes.payment.PurchasedProduct;

@Component
public class StringToPurchasedProductConverter implements Converter<String, PurchasedProduct> {
    @Override
    public PurchasedProduct convert(String source) {
        // Assuming the input format is "productId:quantity"
        String[] parts = source.split(":");
        return new PurchasedProduct(parts[0], Integer.parseInt(parts[1]));
    }
}
