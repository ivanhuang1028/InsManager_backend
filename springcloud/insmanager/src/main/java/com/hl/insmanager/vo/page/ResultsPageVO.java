package com.hl.insmanager.vo.page;

import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.List;

public class ResultsPageVO<T> extends PageVO {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public ResultsPageVO() {
        this(null);
    }

    public static ResultsPageVO init(List list, List ids, PageVO pageVo) {
        ResultsPageVO resultsPageVo = new ResultsPageVO(list);
        resultsPageVo.processPage(ids);
        resultsPageVo.copyPage(pageVo);
        return resultsPageVo;
    }

    public static ResultsPageVO init(List list, List ids) {
        ResultsPageVO resultsPageVo = new ResultsPageVO(list);
        resultsPageVo.processPage(ids);
        return resultsPageVo;
    }

    public static ResultsPageVO init(List list, PageVO pageVo) {
        ResultsPageVO resultsPageVo = new ResultsPageVO(list);
        resultsPageVo.copyPage(pageVo);
        return resultsPageVo;
    }


    public static ResultsPageVO init(List list) {
        ResultsPageVO resultsPageVo = new ResultsPageVO(list);
        return resultsPageVo;
    }

    public ResultsPageVO(List<T> list, PageVO pageVo) {
        if (list instanceof Page) {
            Page page = (Page) list;
            super.setPageIndex(page.getPageNum());
            super.setPageSize(page.getPageSize());
            super.setTotal(page.getTotal());
            this.list = page;
        } else if (list instanceof List) {
            super.setPageIndex(1);
            super.setPageSize(list.size());
            super.setTotal((long) list.size());
            this.list = list;
        } else {
            super.setTotal(0L);
            this.list = new ArrayList<>();
        }
        if (pageVo != null) {
            super.setOpenPage(pageVo.getOpenPage());
        }
    }

    public ResultsPageVO(List<T> list) {
        this(list, null);
    }

    public void processPage(List ids) {
        if (ids != null && ids instanceof Page) {
            Page page = (Page) ids;
            super.setPageIndex(page.getPageNum());
            super.setPageSize(page.getPageSize());
            super.setTotal(page.getTotal());
        }
    }

    public void copyPage(PageVO pageVo) {
        if (pageVo == null) {
            return;
        }
        super.setOpenPage(pageVo.getOpenPage());
        super.setPageIndex(pageVo.getPageIndex());
        super.setPageSize(pageVo.getPageSize());
    }

    public static ResultsPageVO getEmptyResult() {
        return new ResultsPageVO(new ArrayList());
    }

}
