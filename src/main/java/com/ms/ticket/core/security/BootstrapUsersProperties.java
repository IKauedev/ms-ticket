package com.ms.ticket.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.bootstrap")
public class BootstrapUsersProperties {

    private boolean enabled;
    private String adminUsername;
    private String adminPassword;
    private String boxOfficeUsername;
    private String boxOfficePassword;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getBoxOfficeUsername() {
        return boxOfficeUsername;
    }

    public void setBoxOfficeUsername(String boxOfficeUsername) {
        this.boxOfficeUsername = boxOfficeUsername;
    }

    public String getBoxOfficePassword() {
        return boxOfficePassword;
    }

    public void setBoxOfficePassword(String boxOfficePassword) {
        this.boxOfficePassword = boxOfficePassword;
    }
}