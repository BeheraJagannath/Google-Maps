package com.example.models;

import com.google.gson.annotations.SerializedName;

public class ResponseSearch {

	@SerializedName("AdministrativeArea")
	public AdministrativeArea administrativeArea;

	@SerializedName("Type")
	public String type;

	@SerializedName("Version")
	public int version;

	@SerializedName("LocalizedName")
	public String localizedName;

	@SerializedName("Country")
	public Country country;

	@SerializedName("Rank")
	public int rank;

	@SerializedName("Key")
	public String key;

	public void setAdministrativeArea(AdministrativeArea administrativeArea){
		this.administrativeArea = administrativeArea;
	}

	public AdministrativeArea getAdministrativeArea(){
		return administrativeArea;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setVersion(int version){
		this.version = version;
	}

	public int getVersion(){
		return version;
	}

	public void setLocalizedName(String localizedName){
		this.localizedName = localizedName;
	}

	public String getLocalizedName(){
		return localizedName;
	}

	public void setCountry(Country country){
		this.country = country;
	}

	public Country getCountry(){
		return country;
	}

	public void setRank(int rank){
		this.rank = rank;
	}

	public int getRank(){
		return rank;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}
}