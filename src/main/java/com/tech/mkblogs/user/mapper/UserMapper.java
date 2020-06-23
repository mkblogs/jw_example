package com.tech.mkblogs.user.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.tech.mkblogs.model.Authorities;
import com.tech.mkblogs.model.JwtUser;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.dto.UserSessionDTO;

@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mappings({
	      @Mapping(target = "repeatPassword", ignore = true), 
	      @Mapping(target="id", source="user.id"),
	      @Mapping(target="loginName", source="user.loginName"),
	      @Mapping(target="password", source="user.password"),
	      @Mapping(target="firstName", source="user.firstName"),
	      @Mapping(target="lastName", source="user.lastName"),
	      @Mapping(target = "encrypted", source="user.encrypted"), 
	    })
	UserDTO toDTO(JwtUser user);
	
	
	
	@Mappings({
		  @Mapping(target = "accountExpired", ignore = true), 
		  @Mapping(target = "accountLocked", ignore = true), 
		  @Mapping(target = "credentialsExpired", ignore = true), 
		  @Mapping(target = "enabled", ignore = true), 
		  
		  @Mapping(target = "lastLogin", ignore = true), 
		  @Mapping(target = "id", source="dto.id"),
	      @Mapping(target = "loginName", source="dto.loginName"),
	      @Mapping(target = "password", source="dto.password"),
	      @Mapping(target = "firstName", source="dto.firstName"),
	      @Mapping(target = "lastName", source="dto.lastName"),
	      @Mapping(target = "encrypted", source="dto.encrypted"), 
	    })
	JwtUser toModel(UserDTO dto);
	
	
	
	@Mappings({
	      @Mapping(target="id", ignore = true),
	      @Mapping(target="loginName", source="dto.loginName"),
	      @Mapping(target="password", source="dto.password"),
	      @Mapping(target="firstName", source="dto.firstName"),
	      @Mapping(target="lastName", source="dto.lastName"),
	      @Mapping(target="createdBy", ignore = true),
	      @Mapping(target="createdTs", ignore = true),
	      @Mapping(target="lastModifiedBy", ignore = true),
	      @Mapping(target="lastModifiedTs", ignore = true),
	      @Mapping(target="version", ignore = true),	      
	    })
	JwtUser toDTOForUpdate(UserDTO dto,@MappingTarget JwtUser model);
	
	@AfterMapping
	default void afterMapping(UserDTO dto, @MappingTarget JwtUser model) {
		
		if(dto.getRole() != null) {
			if(!dto.getRole().equalsIgnoreCase(model.getRole())) {
				Authorities authorities = new Authorities();
				authorities.setAuthority(model.getRole());
				model.removeAuthorities(authorities);
				
				authorities = new Authorities();
				authorities.setAuthority(dto.getRole());
				model.addAuthorities(authorities);
			}		
		}
	}
	
	@Mappings({
	      @Mapping(target="id", source="user.id"),
	      @Mapping(target="loginName", source="user.loginName"),
	      @Mapping(target="lastLogin", source="user.lastLogin"),
	      @Mapping(target="authority", source="user.role"),
	    })
	UserSessionDTO toSessionUserDTO(JwtUser user);
}