package com.hl.insmanager.vo.page;


import com.hl.common.constants.Constants;

/**
 * 分页参数
 * @author Administrator
 */
public class PageVO {
    private Boolean openPage;
    private Integer pageSize;
    private Integer pageIndex;
    private Long total;

    public Integer getPageSize() {
        return pageSize == null ? Constants.PAGESIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex == null || pageIndex < 1 ? 1 : pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getTotal() {
        return total == null ? 0 : total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Boolean getOpenPage() {
        return openPage == null ? true : openPage;
    }

    public void setOpenPage(Boolean openPage) {
        this.openPage = openPage;
    }
}
