package CMS.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import CMS.utilities.Utilities;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionService {
	private static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	@Autowired
	private Validator validator;

	public ProcessResult create(Optional<EventType> eventTypeOptional) throws JAXBException {
		Optional<ProcessResult> resultOptional = Optional.empty();
		try {
			resultOptional = validator.checkEventType(eventTypeOptional);
			if (resultOptional.isPresent())
				return resultOptional.get();

			EventType eventType = eventTypeOptional.get();
			Subscription subscription = buildSubscription(eventType);
			resultOptional = Optional.of(new ProcessResult(subscription.getIdentifier()));
		} catch (Exception e) {
			logger.error("error while saving order: ", e);
		} finally {
			return resultOptional.orElse(new ProcessResult(EventErrorStatus.UNKNOWN_ERROR));
		}
	}

	public ProcessResult change(Optional<EventType> eventTypeOptional) throws JAXBException {
		Optional<ProcessResult> resultOptional = Optional.empty();
		try {
			resultOptional = validator.checkEventType(eventTypeOptional);
			if (resultOptional.isPresent())
				return resultOptional.get();

			Optional<Subscription> subscriptionOptional = getSubscription(eventTypeOptional);
			resultOptional = validator.checkSubscription(subscriptionOptional);
			if (resultOptional.isPresent())
				return resultOptional.get();

			Utilities.updateApplication(subscriptionOptional.get().getIdentifier(), "");

			Subscription subscription = subscriptionOptional.get();
			String order = eventTypeOptional.get().getPayload().getValue().get(1);
			subscription.getSubscriptionOrder().setEditionCode(order);
			subscription.getSubscriptionOrder().setPricingDuration(order);
			resultOptional = Optional.of(new ProcessResult(subscription.getIdentifier()));
		} catch (Exception e) {
			logger.error("error while changing order: ", e);
		} finally {
			return resultOptional.orElse(new ProcessResult(EventErrorStatus.UNKNOWN_ERROR));
		}
	}

	public ProcessResult cancel(Optional<EventType> eventTypeOptional) throws JAXBException {
		Optional<ProcessResult> resultOptional = Optional.empty();
		try {
			resultOptional = validator.checkEventType(eventTypeOptional);
			if (resultOptional.isPresent())
				return resultOptional.get();

			Optional<Subscription> subscriptionOptional = getSubscription(eventTypeOptional);

			Utilities.deleteApplication(subscriptionOptional.get().getIdentifier());

			Subscription subscription = subscriptionOptional.get();
			subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
			resultOptional = Optional.of(new ProcessResult(subscription.getIdentifier()));
		} catch (Exception e) {
			logger.error("error while canceling order: ", e);
		} finally {
			return resultOptional.orElse(new ProcessResult(EventErrorStatus.UNKNOWN_ERROR));
		}
	}

	private Subscription buildSubscription(EventType eventType) {

		// create an appname for hierarchical configuration management
		String app_name = Utilities.createApplication(eventType.getCreator().uuid);
		Subscription subscription = new Subscription();
		subscription.setIdentifier("dummy-account");

		subscription.setSubscriptionStatus(SubscriptionStatus.VALID);
		return subscription;
	}

	private SubscriptionOrder buildOrder(EventType eventType) {
		SubscriptionOrder subscriptionOrder = new SubscriptionOrder();
		String subscriptionIdentifier = eventType.getPayload().getValue().get(1);
		subscriptionOrder.setId((long) Integer.parseInt(subscriptionIdentifier));
		return subscriptionOrder;
	}

	public Optional<Subscription> getSubscription(Optional<EventType> eventType) {
		String subscriptionIdentifier = eventType.get().getPayload().getValue().get(0);
		String app = Utilities.getApplication(subscriptionIdentifier);
		Subscription subscription = new Subscription();
		subscription.setIdentifier(subscriptionIdentifier);

		return Optional.of(subscription);

	}
}
