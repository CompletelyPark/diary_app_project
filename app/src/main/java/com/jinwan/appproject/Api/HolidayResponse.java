package com.jinwan.appproject.Api;

import java.util.List;

public class HolidayResponse {
    private List<Holiday> response;

    public List<Holiday> getResponse() {
        return response;
    }

    public static class Holiday {
        private String date; // YYYY-MM-DD 형식

        public String getDate() {
            return date;
        }
    }
}
