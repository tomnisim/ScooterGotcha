import * as React from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AwardsApi } from '../API/AwardsApi';
import { UsersApi } from '../API/UsersApi';

import Table from 'rc-table';
import Select from 'react-select'

const background = {uri: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/adminAppDesign/adminApp/assets/background.png'};

const awardsAPI = new AwardsApi();


let awards_list = []
let awards_ids_list = []

let emails = []
let award_to_add = ""




const usersApi = new UsersApi();
let users_emails = ""

const get_users_list = async () => {
  // todo: change 5 to admin id, change params to functions.
  let response = await usersApi.view_users();
  if (!response.was_exception){
    let users_list = response.value
    users_emails = users_list.map((item) => {
      return (
        {value: item._email, label: item._email}
      );
    })
  }
}








const get_awards_list = async () => {
  let response = await awardsAPI.view_awards();
  console.log(response)
  if (!response.was_exception){
    awards_list = response.value


    awards_ids_list = awards_list.map((item) => {
      return (
        {value: item.id, label: item.id}
      );
    })
  }
  console.log(awards_list)
    
}

get_users_list();
get_awards_list();
export default function AwardsWindow({navigation}) {
  get_awards_list()
  get_users_list();
  console.log(awards_list)


  
  const add_to_emails = (text) => {
    emails.push(text)
  }
  const setText_to_award_to_add = (text) => {
    award_to_add = text
  }

  const add_award = () => {
    // todo : build list , or try to transfer array.
    awardsAPI.add_award(emails, award_to_add)
    get_awards_list()
  }



  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Awards List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <View style={styles.container}>
    <Table columns={columns} data={awards_list} tableLayout="auto"/>
    </View>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      <Select
        placeholder="emails to award"
        options={users_emails}
        onChange = {nextText => add_to_emails(nextText.value)}>
      </Select>
      <TextInput
        style={styles.textInputer}
        placeholder="award message"
        onChangeText={newText => setText_to_award_to_add(newText)}
      />
    <Button onPress={() => add_award()} title="Add Award" color="#841584"/>

    
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
    title: "ID",
    dataIndex: "id",
    key: "id",
    width: 200,
  },
  {
    title: "emails",
    dataIndex: "emails",
    key: "emails",
    width:200,
  },
  {
    title: "Award Message",
    dataIndex: "message",
    key: "message",
    width:200,
  },
  {
    title: "Admin",
    dataIndex: "admin_email",
    key: "admin_email",
    width: 200,
  },
  {
    title: "Award Date",
    dataIndex: "message_date",
    key: "message_date",
    width: 200,
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




    




