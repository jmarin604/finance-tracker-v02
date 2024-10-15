package com.finance.tracker.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer transId;

	private String amount;

	private Date creationDate;

	private String creatorUser;

	private Date modificationDate;

	private String modifierUser;

	private String status;

	private LocalDateTime  transactionDate;

	private Integer category;

	private Integer transactionType;

	private Integer user;
}
