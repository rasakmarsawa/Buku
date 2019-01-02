package com.example.x9090.buku;

public class Api {
    public static final String ROOT_URL = "https://bukumanager.000webhostapp.com/v1/Api.php?function=";
    public static final String URL_GET_IMAGE = "https://bukumanager.000webhostapp.com/upload/";

    public static final String URL_CREATE_BOOK = ROOT_URL + "createbuku";
    public static final String URL_READ_BOOKS = ROOT_URL + "getbuku";
    public static final String URL_UPDATE_BOOK = ROOT_URL + "updatebuku";
    public static final String URL_DELETE_BOOK = ROOT_URL + "deletebuku&id=";

}
