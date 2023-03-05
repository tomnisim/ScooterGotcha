import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AdminApi } from '../API/AdminApi';

const adminApi = new AdminApi();
let admins_list = []

let user_email_to_appoint = "none"
let password = ""
let phoneNumber = ""
let birthDay = ""
let gender = ""

let user_email_to_remove_appoint = "none"

const get_admins_list = async () => {
  // todo: change 5 to admin id, change params to functions.
  let response = await adminApi.view_admins();
  if (!response.was_exception){
    admins_list = response.value
    console.log(response.value)

  }
}


export default function AdminsWindow({navigation}) {
  get_admins_list()


  const setText_email_to_appoint = (text) => {
    user_email_to_appoint = text;
    }
    const setText_password = (text) => {
      password = text;
      }
    const setText_phone = (text) => {
      phoneNumber = text;
    }
    const setText_gender = (text) => {
      gender = text;
    }
    const setText_birthday = (text) => {
      birthDay = text;
    }
  

  const setText_email_to_remove_appoint = (text) => {
    user_email_to_remove_appoint = text;
    }



  const add_admin = () => {
    alert(user_email_to_appoint);
    adminApi.add_admin(user_email_to_appoint, password, phoneNumber, birthDay, gender);
    get_admins_list();
  }

  const delete_admin = () => {
    alert(user_email_to_remove_appoint)
    adminApi.delete_admin(user_email_to_remove_appoint)
    get_admins_list()

  }



  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
    <Text style={{fontSize: 30, borderColor: "gray"}}><b>Admins List:</b></Text>
    <View style={styles.container}>
    {admins_list.map((item) => {
        return (
          <View>
            <Text style={styles.item}>{"email: "+ item._email +", gender: "+ item._gender +", phone: "+ item._phone_number + 
            " appointed_by: "+ item._appointed_by +", appointmentDate: " + item._appointment_date}</Text>
          </View>
          
        );
      })}
    <View style={styles.hairline} />
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
    
    <TextInput
          style={{height: 40}}
          placeholder="User email to appoint"
          onChangeText={newText => setText_email_to_appoint(newText)}
        />
      <TextInput
        style={{height: 40}}
        placeholder="Password"
        onChangeText={newText => setText_password(newText)}
      />
      <TextInput
        style={{height: 40}}
        placeholder="Phone"
        onChangeText={newText => setText_phone(newText)}
      />
      <TextInput
        style={{height: 40}}
        placeholder="Gender"
        onChangeText={newText => setText_gender(newText)}
      />
      <TextInput
        style={{height: 40}}
        placeholder="Birth Day"
        onChangeText={newText => setText_birthday(newText)}
      />
      <Button onPress={() => add_admin()} title="Add Admin" color="#007fff"/>
      <TextInput
          style={{height: 40}}
          placeholder="User email to delete appoint"
          onChangeText={newText => setText_email_to_remove_appoint(newText)}
        />
      <Button onPress={() => delete_admin()} title="Delete Admin" color="#007fff"/>
    
  </View>
  </View>
  </View>

);


























}



const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 50,
  },
  hairline: {
    backgroundColor: '#A2A2A2',
    height: 2,
    width: 800
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});




    