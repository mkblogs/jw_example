package com.tech.mkblogs.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Account extends BaseModel {
	
	@Transient
    public static final String SEQUENCE_NAME = "account_sequence";
	
	private String accountName;	
	private String accountType;
	private BigDecimal amount;	
	
	Account(Integer id,String accountName,String accountType,BigDecimal amount){
		super(id);
		this.accountName = accountName;
		this.accountType = accountType;
		this.amount = amount;
	}
	
	@Override
    public String toString() {
      ObjectMapper mapper = new ObjectMapper();
      String jsonString = "";
    try {
    	//SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
       // df.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
      jsonString = mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
      return jsonString;
    }
}
