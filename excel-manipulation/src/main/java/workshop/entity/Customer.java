package workshop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class Customer implements Serializable {

	@Id
	@Column(name = "CUSTOMER_ID")
	private Integer customerId;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	private String address;

	private String city;

	@Column(name = "POSTAL_CODE")
	private String postalCode;

	private String country;
}
