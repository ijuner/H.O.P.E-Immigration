package ca.conestoga.project.product.bo.admin;

/**
 * page object of admin info
 */
public class PageAdminInfo {
    private int    id;        // primary key
    private String username;  // username
    private String realname;  // real name
    private int    status;    // status
    private String statusStr; // status string
    private String color;     // css color

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PageAdminInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
