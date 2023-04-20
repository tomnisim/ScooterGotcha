

import React, { useState } from 'react';
import DatePicker from "react-datepicker";
import {ImageBackground, View, Text, Button, StyleSheet, TextInput, TouchableOpacity} from 'react-native';
import { background } from '../API/Paths';
import "react-datepicker/dist/react-datepicker.css";
import { UserApi } from '../API/UserApi';


export default function RegisterScreen({navigation}) {
  const [email, setEmail] = useState('tom@gmail.com');
  const [password, setPassword] = useState('Tn12345678');
  const [firstName, setFirstName] = useState('tt');
  const [lastName, setLastName] = useState('nn');
  const [phoneNumber, setPhoneNumber] = useState('0524568759');
  const [birthday, setBirthday] = useState(new Date('1996-05-05'));
  const [gender, setGender] = useState('male');
  const [scooterType, setScooterType] = useState('ty');
  const [rpSerial, setRpSerial] = useState('first12345');
  const [licenseDate, setLicenseDate] = useState(new Date());
  
  const userApi = new UserApi()

  const date_to_string = (date)=>{
    const year = date.getFullYear(); // get the year (e.g. 2023)
    const month = date.getMonth() + 1; // get the month (0-11) and add 1 to convert it to 1-12
    const day = date.getDate(); // get the day of the month (1-31)

    // format the date as a string in the format "YYYY-MM-DD"
    const dateString = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    console.log(dateString)
    return dateString
  }
  const register = async () => {
    const parsedLicenseDate = date_to_string(licenseDate)
    const parsedBirthday = date_to_string(birthday)
    console.log(parsedLicenseDate)
    console.log(parsedBirthday)
    let response = await userApi.register(email, password, firstName, lastName, phoneNumber, 
      gender, rpSerial, parsedLicenseDate, scooterType, parsedBirthday)
    if (response.was_exception || response.was_exception == undefined){
        if (response.was_exception == undefined)
            alert("no connection")
        else{

        }
            
    }
    else
    {
        console.log(response.message)
        navigation.navigate('Login')

      }
  };

  const [date, setDate] = useState(new Date())



  return (
    <View style={{flex:0.75, padding:10}}>
            <ImageBackground /*source={background}*/ resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Welcome! Please Register</h3></Text>
            
            <TextInput
          style={styles.input}
          placeholder="First Name"
          value={firstName}
          onChangeText={setFirstName}
        />
      <TextInput
          style={styles.input}
          placeholder="Last Name"
          value={lastName}
          onChangeText={setLastName}
        />
      <TextInput
        style={styles.input}
        placeholder="Phone Number"
        value={phoneNumber}
        onChangeText={setPhoneNumber}
      />

      <TextInput
        style={styles.input}
        placeholder="Birthday"
        value={birthday}
        onChangeText={setBirthday}
      />
        
       
      <TextInput
        style={styles.input}
        placeholder="Gender"
        value={gender}
        onChangeText={setGender}
      />
      <view style={{flex:0.75, padding:0}}>
        <input type="radio" value="Male" onClick={() => setGender('male')} name="gender" /> Male
        <input type="radio" value="Female" onClick={() => setGender('female')} name="gender" /> Female
      </view> 
      <TextInput
        style={styles.input}
        placeholder="Scooter Type"
        value={scooterType}
        onChangeText={setScooterType}
      />
      <TextInput
        style={styles.input}
        placeholder="Raspberry Pi Serial"
        value={rpSerial}
        onChangeText={setRpSerial}
      />
      <TextInput
        style={styles.input}
        placeholder="License Date"
        value={licenseDate}
        onChangeText={setLicenseDate}
      />
      <view style={{flex:0.75, padding:0}}>
      <DatePicker  selected={birthday} onChange={(date) => setLicenseDate(date)} />
      </view>
      
      
        <TextInput
          style={styles.input}
          placeholder="Email"
          value={email}
          onChangeText={setEmail}
        />
        <TextInput
          style={styles.input}
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
        /> 

        <Button onPress={() => register()}  title="Register" color="#841584"/>
        <TouchableOpacity onPress={()=> navigation.navigate('Login')}>
                <Text  style={{ color: 'blue', textDecorationLine: 'underline', fontSize:30 }}>{'Already registered? Please Login'}</Text>
              </TouchableOpacity>
            </View>
            </ImageBackground>
        </View>
    
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  input: {
    width: '80%',
    height: 50,
    padding: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderRadius: 5,
  },
  button: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
    marginTop: 10,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
  },
  forgotPassword: {
    marginTop: 10,
    color: '#3498db',
    textDecorationLine: 'underline',
  },
  
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});











      