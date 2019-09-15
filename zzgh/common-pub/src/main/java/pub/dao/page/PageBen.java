package pub.dao.page;

/**
 * Created by Administrator on 2017/5/8.
 */
public class PageBen {

    private Integer page = 0;
    private Integer current = 1;
    private Integer total = 0;

    public PageBen() {
    }

    public PageBen(Page page) {
        this.page = page.getPages();
        this.current = page.getCurrentPage();
        this.total = page.getTotalCount();
    }

    public PageBen(Integer page, Integer current, Integer total) {
        this.page = page;
        this.current = current;
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
