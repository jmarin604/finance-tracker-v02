package com.finance.tracker.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer catId;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="creator_user")
	private String creatorUser;

	private String description;

	@Column(name="modification_date")
	private Date modificationDate;

	@Column(name="modifier_user")
	private String modifierUser;

	private String name;

	private String status;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="category")
	private List<Transaction> transactions;

}