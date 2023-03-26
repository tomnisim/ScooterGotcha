import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { UsersApi } from '../API/UsersApi';
import { useState } from 'react'; 

  


const usersApi = new UsersApi();
let users_list = []

const get_users_list = async () => {
  // todo: change 5 to admin id, change params to functions.
  let response = await usersApi.view_users();
  if (!response.was_exception){
    users_list = response.value
  }
}





let user_email_to_edit = "none"
let user_email_to_delete = "none"

const setText_to_edit = (text) => {  
  user_email_to_edit = text;
}
const setText_to_delete = (text) => {  
  user_email_to_delete = text;
}



const edit_user = () => {
  // usersApi.edit_user();
  console.log(user_email_to_edit);
  alert(user_email_to_edit);
}

const delete_user = () => {
  usersApi.delete_user(user_email_to_delete)
}

export default function UsersWindow({navigation}) {
  get_users_list();


  return (
      <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Text style={{fontSize: 30, borderColor: "gray"}}><b>Users List:</b></Text>
      <View style={styles.container}>
      {users_list.map((item) => {
        return (
          <View>
            <Text style={styles.item}>{"email: "+ item._email +", gender: "+ item._gender +", phone: "+ item._phone_number + 
            " rating: "+ item.rating}</Text>
          </View>
        );
      })}
      <View style={styles.hairline} />
      <View style={{alignItems: 'center', justifyContent: 'center' }}>
      
      <TextInput
        style={{height: 40}}
        placeholder="User Email to edit!"
        onChangeText={newText => setText_to_edit(newText)}
      />
      <Button onPress={() => edit_user()} title="Edit User" color="#007fff"/>
      
      <TextInput
        style={{height: 40}}
        placeholder="User email to delete"
        onChangeText={newText => setText_to_delete(newText)}
      />
      <Button onPress={() => delete_user()} title="Delete User" color="#007fff"/>
      
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




