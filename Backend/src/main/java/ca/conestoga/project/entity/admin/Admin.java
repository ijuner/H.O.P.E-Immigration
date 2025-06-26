package ca.conestoga.project.entity.admin;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * entity for admin account
 */
@Entity
@Table(name = "manage_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;          // primary key
    private String username; // login username
    private String realname; // account user real name
    private String password; // account password
    private int status;      // account status

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime; // create time

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime; // last update time

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    /**
     * admin account status enumeration
     */
    public enum StatusEnum {
        OFF(1, "disable"),
        ON(0, "active");

        private final int value;
        private final String name;

        StatusEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static StatusEnum findStatusByValue(int value) {
            return Arrays.stream(StatusEnum.values()).filter(s -> s.getValue() == value).findFirst().orElse(StatusEnum.OFF);
        }
    }
}
