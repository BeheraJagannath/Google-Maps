package com.example.models;

import com.google.gson.annotations.SerializedName;

public class Country {

	@SerializedName("LocalizedName")
	public String localizedName;

	@SerializedName("ID")
	public String iD;

	public void setLocalizedName(String localizedName){
		this.localizedName = localizedName;
	}

	public String getLocalizedName(){
		return localizedName;
	}

	public void setID(String iD){
		this.iD = iD;
	}

	public String getID(){
		return iD;
	}
}