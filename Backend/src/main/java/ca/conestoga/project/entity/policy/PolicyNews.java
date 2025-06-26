package ca.conestoga.project.entity.policy;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * entity for policy news
 */
@Entity
@Table(name = "policy_news")
public class PolicyNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;           // primary key

    @ManyToOne
    @JoinColumn(name = "policy_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Policy policy;    // related policy

    private String lang;      // policy language

    private String title;     // policy title

    private String content;   // policy content

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;  // create time

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;  // update time

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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


    /**
     * policy language enumeration
     */
    public enum LauguageEnum {
        EN(0, "en"),
        CHS(1, "zh-CN");

        private final int value;
        private final String name;

        LauguageEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static PolicyNews.LauguageEnum findStatusByValue(int value) {
            return Arrays.stream(PolicyNews.LauguageEnum.values()).filter(s -> s.getValue() == value).findFirst().orElse(LauguageEnum.EN);
        }
    }
}
