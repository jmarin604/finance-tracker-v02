package com.finance.tracker.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer userId;

	private Date creationDate;

	private String creatorUser;

	private String email;

	private Date modificationDate;

	private String modifierUser;

	private String password;

	private String status;

	private String username;

	private String token;

}
