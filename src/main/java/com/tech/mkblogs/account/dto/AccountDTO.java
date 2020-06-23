package com.tech.mkblogs.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.GroupSequence;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.tech.mkblogs.validation.First;
import com.tech.mkblogs.validation.Second;
import com.tech.mkblogs.validation.UniqueValueValidator.UniqueValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({First.class,Second.class,AccountDTO.class})
@UniqueValue(
	methodName = "findByAccountName",
	className = "com.tech.mkblogs.account.service.AccountService",
	message = "{account.name.alreadyexists}",groups = Default.class)
@Builder
public class AccountDTO {

	private Integer accountId;
	
	@NotEmpty(message = "{account.name.notempty}",groups = {First.class})
	@Size(min = 3,max = 50,message = "{account.name.size}",groups = {Second.class})
	private String name;
	
	@NotEmpty(message = "{account.type.notempty}",groups = {First.class})
	@Size(min = 2,max = 50,message = "{account.type.size}",groups = {Second.class})
	private String type;
	
	@NotNull(message = "{account.amount.notnull}",groups = {First.class})
	@DecimalMin(value = "99.00",inclusive = false,message = "{account.amount.min}",groups = {Second.class})
	@DecimalMax(value = "1000.00",inclusive = true,message = "{account.amount.max}",groups = {Second.class})
	private BigDecimal amount;
	
	private String createdBy;
	private LocalDateTime createdTs;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedTs;
	private Integer version;
	
	private String createdName;	
	private String lastModifiedName;
}