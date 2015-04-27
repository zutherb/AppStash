package io.github.zutherb.appstash.shop.ui.tracking.mixpanel;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrackingServiceImpl implements TrackingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingServiceImpl.class);

    private MessageBuilder messageBuilder;

    @Autowired
    public TrackingServiceImpl(@Value("${mix.panel.token}") String token) {
        messageBuilder = new MessageBuilder(token);
    }

    @Override
    public void track(OrderInfo order) {
        try {
            ClientDelivery delivery = mapToPurchaseDelivery(order);

            JSONObject user = messageBuilder.set(order.getUser().getId(), new JSONObject());

            MixpanelAPI mixpanel = new MixpanelAPI();
            mixpanel.deliver(delivery);
            mixpanel.sendMessage(user);
        } catch (Exception e) {
            LOGGER.error("Error occurred while tacking", e);
        }
    }

    private ClientDelivery mapToPurchaseDelivery(OrderInfo order) throws JSONException {
        JSONObject properties = new JSONObject();
        properties.put("totel", order.getTotalSum().doubleValue());
        JSONObject purchase = messageBuilder.event(order.getOrderId().toString(), "Purchase", properties);

        ClientDelivery delivery = new ClientDelivery();
        delivery.addMessage(purchase);
        return delivery;
    }
}
