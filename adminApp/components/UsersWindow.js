import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput, ImageBackground, ScrollView} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { UsersApi } from '../API/UsersApi';
import { useState } from 'react'; 
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';



  


const usersApi = new UsersApi();
let users_list = []
let users_emails = ""

export const get_users_list = async () => {
  let response = await usersApi.view_users();
  if (!response.was_exception){
    users_list = response.value
    console.log(users_list)
    users_emails = users_list.map((item) => {
      return (
        {value: item._email, label: item._email}
      );
    })
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
  console.log(user_email_to_edit);
  alert(user_email_to_edit);
}

const delete_user = () => {
  usersApi.delete_user(user_email_to_delete)
}

// get_users_list();
export default function UsersWindow({navigation}) {
  get_users_list();

  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Users List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={users_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Select
        placeholder="User Email to edit!"
        options={users_emails}
        onChange={newText => setText_to_edit(newText)}
      ></Select>
      <Button onPress={() => edit_user()} title="Edit User" color="#841584"/>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
       
      <Select
        placeholder="User email to delete"
        options={users_emails}
        onChange={newText => setText_to_delete(newText.value)}
      ></Select>
      <Button onPress={() => delete_user()} title="Delete User" color="#841584"/>


  </View>
  
  </View>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  </ImageBackground>
  </View>

);

}




const columns = [
  {
    title: "Email",
    dataIndex: "_email",
    key: "_email",
    width: 200,
  },
  {
    title: "Gender",
    dataIndex: "_gender",
    key: "_gender",
    width: 200,
  },
  {
    title: "Phone",
    dataIndex: "_phone_number",
    key: "_phone_number",
    width: 200,
  },
  {
    title: "Rating",
    dataIndex: "_rating",
    key: "_rating",
    width:200,
  },
  {
    title: "Admin",
    dataIndex: "_admin",
    key: "_admin",
    width:200,
  },
  {
    title: "Online",
    dataIndex: "_logged_in",
    key: "_logged_in",
    width:200,
  },
];



const styles = StyleSheet.create({
  container: {
    width:1200,
    height:500,
    padding: 10,
    opacity:0.5,
    backgroundColor:'white'
  },
  hairline: {
    backgroundColor: 'black',
    height: 2,
    width: 200
  },
  textInputer: {
    backgroundColor:'white',
    opacity:0.8,
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});


