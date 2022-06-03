package ru.praktikum_sevices.qa_scooter.model.orders;

public class PageInfo {
    /**
     * POJO класс модели pageInfo
     */

    private int page;
    private int total;
    private int limit;

    public PageInfo(int page, int total, int limit) {
        this.page = page;
        this.total = total;
        this.limit = limit;
    }

    public PageInfo() {
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }


    public PageInfo setPage(int page) {
        this.page = page;
        return this;
    }

    public PageInfo setTotal(int total) {
        this.total = total;
        return this;
    }

    public PageInfo setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "page=" + page +
                ", total=" + total +
                ", limit=" + limit +
                '}';
    }
}
