package co.edu.eafit.bank.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
@Entity
@Table(name = "customer", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cust_id", unique = true, nullable = false)
	@NotNull
	private Integer custId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doty_id")
	@NotNull
	private DocumentType documentType;

	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "address", nullable = false)
	private String address;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "email", nullable = false)
	private String email;
	@NotNull
	@NotEmpty
	@Size(max = 1)
	@Column(name = "enable", nullable = false)
	private String enable;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "name", nullable = false)
	private String name;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "token")
	private String token;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Account> accounts = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<RegisteredAccount> registeredAccounts = new ArrayList<>();

}