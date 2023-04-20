
import { useState } from "react";

export class User{
    constructor(data){
            this.email =  data._email;
            this.firstName = data.name;
            this.lastName = data.lastName;
            this.phoneNumber = data._phone_number;
            this.birthday = new Date(data._birth_day);
            this.gender = data._gender;
            this.scooterType = data.scooterType;
            this.rpSerial = data.raspberryPiSerialNumber;
            this.licenseDate   = new Date(data.licenceIssueDate);
        }


    
    

    
}