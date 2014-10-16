package com.starbattle.accounts.manager;

public class PlayerAccount {

	private String name;
	private String password;
	private String email;
	private int gold;
	private String displayName; 
	
	public PlayerAccount()
	{
		
	}
	
	public PlayerAccount(String name, String passwort, String email){
		this.name = name; 
		this.password = passwort;
		this.email = email;
	}
	
	public PlayerAccount(String name, String displayName, String email, int gold) {
		this.name = name;
		this.setDisplayName(displayName);
		this.email = email;
		this.gold = gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getGold() {
		return gold;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
