package com.hiphurra.myhorse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageList {

    private final List<String> items;
    private final int maxListSizePerPage;

    public MessageList(List<String> items, int maxListSizePerPage) {
        this.items = items;
        this.maxListSizePerPage = maxListSizePerPage;
    }

    public List<String> getPage(int pageNumber) {
        int fromIndex = ((pageNumber-1) * getMaxListSizePerPage());
        List<String> pageItems = new ArrayList<>();

        for (int i = fromIndex; i < items.size(); i++) {
            pageItems.add(items.get(i));

            if(pageNumber == getNumberOfPages() && (items.size() == i)) {
                break;
            }
            if(i == (fromIndex + getMaxListSizePerPage()) - 1) {
                break;
            }
        }
        return pageItems;
    }

    public Map<Integer, List<String>> getPages() {
        Map<Integer, List<String>> itemsInPages = new HashMap<>();
        for (int i = 0; i < getNumberOfPages(); i++) {
            itemsInPages.put(i, getPage(i));
        }
        return itemsInPages;
    }

    public int getNumberOfPages() {
        return (int) Math.ceil((double) items.size() / getMaxListSizePerPage());
    }

    public int getMaxListSizePerPage() {
        return maxListSizePerPage;
    }

    public List<String> getItems() {
        return items;
    }
}
