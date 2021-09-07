package com.example.vaccination;

public class CentreModel {
    String centreName,centreAddress,startTime,endTime,vaccineName,type;
    int ageLimit,slotsRemaining;

    public CentreModel(String centreName, String centreAddress, String startTime, String endTime, String vaccineName, String type, int ageLimit, int slotsRemaining) {
        this.centreName = centreName;
        this.centreAddress = centreAddress;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vaccineName = vaccineName;
        this.type = type;
        this.ageLimit = ageLimit;
        this.slotsRemaining = slotsRemaining;
    }
}
