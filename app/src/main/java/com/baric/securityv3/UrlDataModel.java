package com.baric.securityv3;

public class UrlDataModel {

    private String id;
    private String realFileName;
    private String FileName;
    private String url;

    public UrlDataModel(String id, String url, String fileName, String realFileName) {
        this.id = id;
        this.url = url;
        this.FileName = fileName;
    }
    public UrlDataModel() {
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

