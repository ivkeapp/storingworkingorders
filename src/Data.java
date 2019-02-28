/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Korisnik Nepo System
 */
public class Data {
    
    int ID;
    String name;
    String device;
    String deviceModel;
    String serialNumber;
    int optional;
    String decription;

    public Data(int ID, String name, String device, String deviceModel, String serialNumber, int optional, String decription) {
        this.ID = ID;
        this.name = name;
        this.device = device;
        this.deviceModel = deviceModel;
        this.serialNumber = serialNumber;
        this.optional = optional;
        this.decription = decription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getOptional() {
        return optional;
    }

    public void setOptional(int optional) {
        this.optional = optional;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
    
    
    
    
}
