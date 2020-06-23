package com.tech.mkblogs.user.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilterDTO {
	
	private String username;	
	private String firstName;
	private String lastName;
}
