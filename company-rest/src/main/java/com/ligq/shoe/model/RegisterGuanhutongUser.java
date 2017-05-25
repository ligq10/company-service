package com.ligq.shoe.model;

import java.util.UUID;

public class RegisterGuanhutongUser {
	
	private String uuid;
	
	private String loginName;
	
	private String password;
	
	private boolean isencrypted;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isIsencrypted() {
		return isencrypted;
	}

	public void setIsencrypted(boolean isencrypted) {
		this.isencrypted = isencrypted;
	}
}
