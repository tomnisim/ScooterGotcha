

import React, { useState } from 'react';
import DatePicker from "react-datepicker";
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { background } from '../API/Paths';
import { useEffect } from 'react';
import "react-datepicker/dist/react-datepicker.css";


export default function Profile({navigation}) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [birthday, setBirthday] = useState(new Date());
  const [gender, setGender] = useState('');
  const [scooterType, setScooterType] = useState('');
  const [rpSerial, setRpSerial] = useState('');
  const [licenseDate, setLicenseDate] = useState(new Date());
  
  useEffect(() => {
    load_user_details();
  }, {})
  const load_user_details = () =>{

  }
  async function update_user_details(){
    // let response = await ridesApi.view_rides();
    // if (!response.was_exception){
    //   setRides_list(response.value)
    // }
  }

  
  const [date, setDate] = useState(new Date())



  return (
    <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>My Profile</h3></Text>
            
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
        value={rpSerial}
        onChangeText={setRpSerial}
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

        <Button onPress={() => update_user_details()}  title="Save Changes" color="#841584"/>
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











      