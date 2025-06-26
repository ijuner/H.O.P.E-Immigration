package ca.conestoga.project.entity.admin;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * entity for operation log
 */
@Entity
@Table(name = "manage_log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;            // primary key
    private String account;    // operation account name

    private String operation;  // operation name

    private String oldContent; // old content

    private String newContent; // new content

    private int type;          // operation type

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;   // create time

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;   // last update time

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "Log{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", operation='" + operation + '\'' +
                ", oldContent='" + oldContent + '\'' +
                ", newContent='" + newContent + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    /**
     * operation type enumeration
     */
    public enum TypeEnum {
        ADMIN(0, "admin"),
        API(1, "api");
        private final int value;
        private final String name;

        TypeEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * operation name enumeration
     */
    public enum ActionEnum {
        ADD_ADMIN("添加后台账号"),
        EDIT_ADMIN("修改后台账号"),
        ADD_ROLE("添加角色"),
        EDIT_ROLE("修改角色名"),
        BIND_ROLE("绑定角色"),
        UNBIND_ROLE("解绑角色"),
        BIND_PRIVILEGE("绑定权限"),
        UNBIND_PRIVILEGE("解绑权限"),
        EDIT_PASSWORD("修改密码"),
        RESET_PASSWORD("重置密码");
        private final String name;

        ActionEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
