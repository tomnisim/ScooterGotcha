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
let rp_waiting_list = []
let users_emails = ""

export const get_users_list = async () => {
  let response = await usersApi.view_users();
  if (!response.was_exception){
    users_list = response.value
    users_emails = users_list.map((item) => {
      return (
        {value: item.userEmail, label: item.userEmail}
      );
    })
  }
  let response1 = await usersApi.view_waiting_rp();
  if (!response1.was_exception){
    rp_waiting_list = response1.value
  }
  console.log(response1)

}





let user_rp_to_add = ""
let user_email_to_delete = ""

const setText_to_add = (text) => {  
  user_rp_to_add = text;
}
const setText_to_delete = (text) => {  
  user_email_to_delete = text;
}

const add_user_rp = () => {
  usersApi.add_user_rp(user_rp_to_add)
}

const delete_user = () => {
  usersApi.delete_user(user_email_to_delete)
}

export default function UsersWindow({navigation}) {
  get_users_list();

  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">

    <View style={{display: 'flex', flexDirection:'row'}}>

    <View style={{display: 'flex', flexDirection:'column'}}>
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Riders List:</b></Text>

    <ScrollView style={styles.container}>
    <Table columns={riders_columns} data={users_list} tableLayout="auto"/>
    </ScrollView>
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Waiting RP List:</b></Text>

    <ScrollView style={styles.container1}>
    <Table columns={waiting_columns} data={rp_waiting_list} tableLayout="auto"/>
    </ScrollView>
    </View>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <TextInput
        style={styles.textInputer}
        placeholder="RP Serial Number"
        onChangeText={newText => setText_to_add(newText)}
      ></TextInput>
      <Button onPress={() => add_user_rp()} title="Add User RP Serial Number" color="#841584"/>
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




const riders_columns = [
  {
    title: "Email",
    dataIndex: "userEmail",
    key: "userEmail",
    width: 200,
  },

  {
    title: "Rating",
    dataIndex: "rating",
    key: "rating",
    width:200,
  },
  {
    title: "Scooter Type",
    dataIndex: "scooterType",
    key: "scooterType",
    width:200,
  },
  {
    title: "RP Serial #",
    dataIndex: "raspberryPiSerialNumber",
    key: "raspberryPiSerialNumber",
    width:200,
  },
  {
    title: "Name",
    dataIndex: "name",
    key: "name",
    width:200,
  },
  {
    title: "Last Name",
    dataIndex: "lastName",
    key: "lastName",
    width:200,
  },
  {
    title: "Gender",
    dataIndex: "gender",
    key: "gender",
    width: 200,
  },
  {
    title: "Phone",
    dataIndex: "phoneNumber",
    key: "phoneNumber",
    width: 200,
  },
  {
    title: "Birth Date",
    dataIndex: "birthDay",
    key: "birthDay",
    width: 200,
  },
  {
    title: "Licence Issue Date",
    dataIndex: "licenceIssueDate",
    key: "licenceIssueDate",
    width:200,
  },
  {
    title: "Online",
    dataIndex: "loggedIn",
    key: "loggedIn",
    width:200,
  },
];

const waiting_columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
    width: 200,
  },
  {
    title: "Raspberry Pi Serial Code",
    dataIndex: "rp_serial_number",
    key: "rp_serial_number",
    width: 200,
  },
];



const styles = StyleSheet.create({
  container: {
    width:1200,
    height:400,
    padding: 10,
    opacity:0.5,
    backgroundColor:'white'
  },
  container1: {
    width:1200,
    height:180,
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
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});


