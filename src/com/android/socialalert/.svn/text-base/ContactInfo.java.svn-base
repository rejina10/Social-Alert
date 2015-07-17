package com.android.socialalert;

public class ContactInfo {
    public String contactFirstName;
    public String contactLastName;
    public String contactEmailAddress;
    public String contactType;
    public String isActive;
    public int isUserConnected;
    public int userStatus;

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFName) {
        this.contactFirstName = contactFName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLName) {
        this.contactLastName = contactLName;
    }

    public String getContactEmailAddressName() {
        return contactEmailAddress;
    }

    public void setContactEmailAddressName(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getUserIsActive() {
        return isActive;
    }

    public void setUserIsActive(String active) {
        this.isActive = active;
    }

    public int getUserIsConnected() {
        return isUserConnected;
    }

    public void setUserIsConnected(int userConnected) {
        this.isUserConnected = userConnected;
    }

    @Override
    public ContactInfo clone() throws CloneNotSupportedException {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.contactEmailAddress = contactEmailAddress;
        contactInfo.contactFirstName = contactFirstName;
        contactInfo.contactLastName = contactLastName;
        contactInfo.contactType = contactType;
        contactInfo.isActive = isActive;
        contactInfo.isUserConnected = isUserConnected;
        contactInfo.userStatus = userStatus;

        return contactInfo;
    }
}
