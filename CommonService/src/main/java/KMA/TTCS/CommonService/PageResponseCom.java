package KMA.TTCS.CommonService;
import java.io.Serializable;


public class PageResponseCom<T> implements Serializable {
    int size;
    int totalElements;
    int totalPages;
    int number;
    T items;

    public PageResponseCom(int size, int totalElements, int totalPages, int number, T items) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
        this.items = items;
    }

    public PageResponseCom() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
