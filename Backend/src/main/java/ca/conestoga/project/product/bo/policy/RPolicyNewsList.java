package ca.conestoga.project.product.bo.policy;

/**
 * policy news list request
 */
public class RPolicyNewsList {
    private int page;    // page of list
    private String lang; // language of content
    private int size;    // page size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
