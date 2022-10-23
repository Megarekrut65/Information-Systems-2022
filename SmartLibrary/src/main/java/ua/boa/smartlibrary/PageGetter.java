package ua.boa.smartlibrary;

import java.util.List;

public class PageGetter<T> {
    public List<T> getPart(List<T> list, int page, int perPage){
        int start = Math.min(list.size(),Math.max((page-1)*perPage, 0)), end = Math.min(list.size(), page * perPage);
        return list.subList(start, end);
    }
}
