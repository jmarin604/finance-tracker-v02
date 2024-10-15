package com.finance.tracker.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer catId;

	private Date creationDate;

	private String creatorUser;

	private String description;

	private Date modificationDate;

	private String modifierUser;

	private String name;

	private String status;

}
