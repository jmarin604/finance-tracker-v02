package com.finance.tracker.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the transactions database table.
 * 
 */
@Entity
@Table(name="transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="trans_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transId;

	private String amount;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="creator_user")
	private String creatorUser;

	@Column(name="modification_date")
	private Date modificationDate;

	@Column(name="modifier_user")
	private String modifierUser;

	private String status;

	@Column(name="transaction_date")
	private LocalDateTime  transactionDate;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	//bi-directional many-to-one association to TransactionType
	@ManyToOne
	@JoinColumn(name="trans_typ_id")
	private TransactionType transactionType;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}