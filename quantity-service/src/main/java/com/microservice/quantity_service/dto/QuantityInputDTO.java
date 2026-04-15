package com.microservice.quantity_service.dto;


import lombok.Data;

@Data
public class QuantityInputDTO {

    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;
    private String username;
    

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

    
	public QuantityDTO getThisQuantityDTO() {
		return thisQuantityDTO;
	}
	
	public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
		this.thisQuantityDTO = thisQuantityDTO;
	}
	
	public QuantityDTO getThatQuantityDTO() {
		return thatQuantityDTO;
	}
	public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
		this.thatQuantityDTO = thatQuantityDTO;
	}
}