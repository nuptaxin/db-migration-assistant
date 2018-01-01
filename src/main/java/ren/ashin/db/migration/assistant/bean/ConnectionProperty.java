package ren.ashin.db.migration.assistant.bean;

import java.util.Date;

/**
 * @ClassName: ConnectionProperty
 * @Description: TODO
 * @author renzx
 * @date Dec 13, 2017
 */
public class ConnectionProperty {
    private String ip;
    private Integer port;
    private String instance;
    private String name;
    private String password;
    private Date lastLoginTime;
    private Boolean isDefault;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return name + "@" + ip + ":" + port + "/" + instance;
    }


}
