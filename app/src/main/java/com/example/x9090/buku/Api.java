package com.example.x9090.buku;

public class Api {
    public static final String ROOT_URL = "http://192.168.43.107:8080/buku/v1/Api.php?function=";
    public static final String URL_GET_IMAGE = "http://192.168.43.107:8080/buku/upload/";

    public static final String URL_CREATE_BOOK = ROOT_URL + "createbuku";
    public static final String URL_READ_BOOKS = ROOT_URL + "getbuku";
    public static final String URL_UPDATE_BOOK = ROOT_URL + "updatebuku";
    public static final String URL_DELETE_BOOK = ROOT_URL + "deletebuku&id=";

}
