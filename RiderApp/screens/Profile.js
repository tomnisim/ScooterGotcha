

import React, { useState } from 'react';
import DatePicker from "react-datepicker";
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { background } from '../API/Paths';
import { useEffect } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import { UserApi } from '../API/UserApi';
import { Utils } from '../Utils/Utils';

var utils = new Utils();
export default function ProfileScreen({navigation}) {
  
  const [user, setUser] = useState(null);
  console.log(user)
  useEffect( () => {
      const fetchData = async () => {
        const temp = await utils.getData('connected_user');
        setUser(temp);
      }
      fetchData();
  }, []);

  if (!user) {
    return (
      <Text>Waiting....</Text>
    )
  }
else {
  return (
    <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>My Profile</h3></Text>

            <View style={styles.container}>
      <View style={styles.infoContainer}>
        <View style={styles.row}>
          <Text style={styles.cellName}>Last Name:</Text>
          <Text style={styles.cellValue}>{user.lastName}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>Phone Number:</Text>
          <Text style={styles.cellValue}>{user.phoneNumber}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>Gender:</Text>
          <Text style={styles.cellValue}>{user.gender}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>Scooter Type:</Text>
          <Text style={styles.cellValue}>{user.scooterType}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>Raspberry Pi Serial:</Text>
          <Text style={styles.cellValue}>{user.rpSerial}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>License Date:</Text>
          <Text style={styles.cellValue}>{user.licenseDate}</Text>
        </View>

        <View style={styles.row}>
          <Text style={styles.cellName}>Email:</Text>
          <Text style={styles.cellValue}>{user.email}</Text>
        </View>
      </View>
    </View>
        
          
 
      

        <Button onPress={() => navigation.navigate('ChangePassword')}  title="Change Password" color="#841584"/>
            </View>
            </ImageBackground>
        </View>
    
  );
}
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f8f8f8',
    alignItems: 'center',
    paddingTop: 20,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 30,
    color: '#333',
  },
  infoContainer: {
    width: '80%',
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 20,
    elevation: 5, // Add shadow for Android
    shadowColor: '#000', // Add shadow for iOS
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.3,
    shadowRadius: 2,
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 15,
  },
  cellName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#333',
    marginRight: 10,
    minWidth: 150,
  },
  cellValue: {
    fontSize: 18,
    flex: 1,
    color: '#555',
  },
});











      