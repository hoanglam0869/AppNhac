package com.hoanglam0869.appnhac.Service;

public class APIService {

    private static String base_url = "https://hoanglam0869.000webhostapp.com/AmNhac/Server/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
