package CMS.model;

import lombok.Data;

import javax.persistence.*;

import CMS.model.Company;
import CMS.model.User;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    private SubscriptionOrder subscriptionOrder;

    private String identifier;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SubscriptionOrder getSubscriptionOrder() {
		return subscriptionOrder;
	}

	public void setSubscriptionOrder(SubscriptionOrder subscriptionOrder) {
		this.subscriptionOrder = subscriptionOrder;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}



}
