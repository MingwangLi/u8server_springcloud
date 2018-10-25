package com.jmsht.u8server.admin.util;

import com.github.pagehelper.Page;

import java.util.List;

public class JQueryUIData {

    private Long total;
    private List<?> rows;

    public static JQueryUIData init(Page<?> page) {
        JQueryUIData instance = new JQueryUIData();
        instance.setRows(page.getResult());
        instance.setTotal(page.getTotal());
        return instance;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }


}
