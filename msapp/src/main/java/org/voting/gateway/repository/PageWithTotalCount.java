package org.voting.gateway.repository;

import org.springframework.data.domain.Page;

/**
 * Created by defacto on 6/7/2018.
 */
public class PageWithTotalCount<T> {
    private int total;
    private Page<T> page;

    public PageWithTotalCount(int total, Page<T> page) {
        this.total = total;
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public Page<T> getPage() {
        return page;
    }
}
