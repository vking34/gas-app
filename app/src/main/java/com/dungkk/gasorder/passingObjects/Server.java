package com.dungkk.gasorder.passingObjects;

public class Server {
    private final static String address = "http://192.168.1.2";
    private final static String IPaddress = "192.168.1.2";

    public static String getIPaddress() {
        return IPaddress;
    }

    public static String getAddress() {
        return address;
    }
}
