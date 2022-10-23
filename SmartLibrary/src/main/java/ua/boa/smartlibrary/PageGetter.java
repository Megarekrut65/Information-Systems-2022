package ua.boa.smartlibrary;

import java.util.Comparator;
import java.util.List;

public class PageGetter<T> {
    public List<T> getPart(List<T> list, int page, int perPage, Comparator<T> comparator){
        int start = Math.min(list.size(),Math.max((page-1)*perPage, 0)), end = Math.min(list.size(), page * perPage);
        list.sort(comparator);
        return list.subList(start, end);
    }
}
