package com.jinwan.appproject.recycler;// MyRepository.java
import java.util.ArrayList;
import java.util.List;

//  데이터의 crud 작업을 수행한다.
//  현재는 메모리 내의 데이터를 사용하지만,
//  실제 애플리케이션에서는 db에서 데이터를 가져올 수 있다
public class MyRepository {
    // Dummy data for illustration
    private List<MyData> dataList = new ArrayList<>();

    public MyRepository() {
        // Initializing with some dummy data
        dataList.add(new MyData("Event 1", "Description for Event 1"));
        dataList.add(new MyData("Event 2", "Description for Event 2"));
    }

    // Fetch data from repository
    public List<MyData> fetchData() {
        return dataList;
    }

    // Optionally, add methods to add, update, or delete data
}
