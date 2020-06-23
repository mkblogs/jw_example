package com.tech.mkblogs.account.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterDTO {
	private String accountName;
	private String accountType;
	private String amount;
	private boolean isFromSearch;
}
