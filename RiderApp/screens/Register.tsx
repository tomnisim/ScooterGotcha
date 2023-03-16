

import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text } from 'react-native';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import Gender_selector from '../components/DatePicker';
const Register = () => {
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
  


  const handleLogin = () => {
    // Handle login logic here
  };

  const [date, setDate] = useState(new Date())



  return (
    
    <View style={styles.container}>
      
      <Text style={styles.title}>Hey! Please Register</Text>

      <TextInput
        style={styles.input}
        placeholder="First Name"
        secureTextEntry={true}
        value={firstName}
        onChangeText={setFirstName}
      />
    <TextInput
        style={styles.input}
        placeholder="Last Name"
        secureTextEntry={true}
        value={lastName}
        onChangeText={setLastName}
      />
      <TextInput
        style={styles.input}
        placeholder="Phone Number"
        secureTextEntry={true}
        value={phoneNumber}
        onChangeText={setPhoneNumber}
      />
      <Text>Birthday</Text>
      <DatePicker selected={birthday} onChange={(date) => setBirthday(date)} />
      
      <TextInput
        style={styles.input}
        placeholder="Gender"
        secureTextEntry={true}
        value={gender}
        onChangeText={setGender}
      />
      <TextInput
        style={styles.input}
        placeholder="Scooter Type"
        secureTextEntry={true}
        value={scooterType}
        onChangeText={setScooterType}
      />
      <TextInput
        style={styles.input}
        placeholder="Raspberry Pi Serial"
        secureTextEntry={true}
        value={rpSerial}
        onChangeText={setRpSerial}
      />
      <Text>License Date</Text>
      <DatePicker  selected={birthday} onChange={(date) => setLicenseDate(date)} />
      <div>
        <input type="radio" value="Male" onClick={() => setGender('male')} name="gender" /> Male
        <input type="radio" value="Female" onClick={() => setGender('female')} name="gender" /> Female
      </div>
{/* <Gender_selector></Gender_selector> */}

      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        secureTextEntry={true}
        value={password}
        onChangeText={setPassword}
      />
      
      <TouchableOpacity style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Register</Text>
      </TouchableOpacity>
      
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

export default Register;
