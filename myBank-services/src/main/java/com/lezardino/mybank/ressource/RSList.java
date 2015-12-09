package com.lezardino.mybank.ressource;

import java.util.ArrayList;
import java.util.List;

public class RSList<T> extends RSLink {

    /** Le nombre de résultats. */
    private Long resultsCount = null;

    /** La items de ressourcess. */
    private List<T> items = new ArrayList<>();

    public RSList(String uriPattern, Long resultsCount, List<T> items) {
        super(uriPattern);
        this.resultsCount = resultsCount;
        this.items = items;
    }

    public Long getResultsCount() {
        return resultsCount;
    }

    public List<T> getItems() {
        return items;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "RSList{" +
                "resultsCount=" + resultsCount +
                ", items=" + items +
                "} " + super.toString();
    }
}
