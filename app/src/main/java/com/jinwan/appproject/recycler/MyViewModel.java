package com.jinwan.appproject.recycler;// MyViewModel.java

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<MyData>> dataList;
    private MyRepository repository;

    public MyViewModel() {
        repository = new MyRepository();
    }

    public LiveData<List<MyData>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            loadDataFromDatabase();
        }
        return dataList;
    }

    public void loadDataFromDatabase() {
        // Fetch data from the repository and set it to LiveData
        List<MyData> data = repository.fetchData();
        dataList.setValue(data);
    }
}
