import com.example.coreapi.delivery.GetDeliveryFeesQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

class JacksonTest {

    @Test
    void testDeserialization() throws Exception {
        String json = """
                {
                  "order": {
                    "weights": [19.0, 20.0, 25.0],
                    "targetLng": 2.0,
                    "targetLat": 1.0
                  }
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        GetDeliveryFeesQuery query = mapper.readValue(json, GetDeliveryFeesQuery.class);

        assert query.getOrder().getTargetLat() == 1.0;
    }
}