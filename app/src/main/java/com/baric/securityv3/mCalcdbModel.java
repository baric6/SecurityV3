package com.baric.securityv3;

public class mCalcdbModel {
    
    private String startTime;
    private String startmiles;
    private String startPlace;
    private String endTime;
    private String endMiles;
    private String endPlace;
    private String totalMiles;

    public mCalcdbModel(String startTime, String startmiles, String startPlace, String endTime, String endMiles, String endPlace, String totalMiles) {
        this.startTime = startTime;
        this.startmiles = startmiles;
        this.startPlace = startPlace;
        this.endTime = endTime;
        this.endMiles = endMiles;
        this.endPlace = endPlace;
        this.totalMiles = totalMiles;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartmiles() {
        return startmiles;
    }

    public void setStartmiles(String startmiles) {
        this.startmiles = startmiles;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndMiles() {
        return endMiles;
    }

    public void setEndMiles(String endMiles) {
        this.endMiles = endMiles;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(String totalMiles) {
        this.totalMiles = totalMiles;
    }

    @Override
    public String toString() {
        return "mCalcdbModel{" +
                "startTime='" + startTime + '\'' +
                ", startmiles='" + startmiles + '\'' +
                ", startPlace='" + startPlace + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endMiles='" + endMiles + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", totalMiles='" + totalMiles + '\'' +
                '}';
    }
}
