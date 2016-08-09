package CMS.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import CMS.model.User;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude={"id", "customers"})
public class SubscriptionOrder {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEditionCode() {
		return editionCode;
	}
	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}
	public String getPricingDuration() {
		return pricingDuration;
	}
	public void setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
	}
	public List<User> getCustomers() {
		return customers;
	}
	public void setCustomers(List<User> customers) {
		this.customers = customers;
	}
	@Id
    @GeneratedValue
    private Long id;
    private String editionCode;
    private String pricingDuration;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> customers;
}
