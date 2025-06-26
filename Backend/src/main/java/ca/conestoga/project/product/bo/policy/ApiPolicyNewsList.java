package ca.conestoga.project.product.bo.policy;

import ca.conestoga.project.entity.policy.PolicyNews;

import java.util.List;

/**
 * business object of policy list for api
 */
public class ApiPolicyNewsList {
    private List<PolicyNews> list;
    private int page;
    private int pageSize;
    private int totalPage;
    private String lang;

    public List<PolicyNews> getList() {
        return list;
    }

    public void setList(List<PolicyNews> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public static class PolicyNews {
        private int id;
        private String title;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
