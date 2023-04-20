import React,{ useState } from 'react';
import { useEffect } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView } from 'react-native';
import { AwardsApi } from '../API/AwardsApi';
import { UsersApi } from '../API/UsersApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';

const awardsAPI = new AwardsApi();
const usersApi = new UsersApi();




export default function AwardsWindow({navigation}) {
  const [awards_list, setAwards_list] = useState([])
  const [awards_ids_list, setAwards_ids_list] = useState([])
  const [emails, setEmails] = useState([])
  const [award_to_add, setText_to_award_to_add] = useState('')
  const [users_emails, setUsers_emails] = useState('')
  
  async function get_emails_list (){
    let response = await usersApi.view_users();
    if (!response.was_exception){
      let temp = response.value
      let temp1 = temp.map((item) => {
        return (
          {key: item.userEmail}
        );
      })
      let list_temp = []
      temp1.map((item) => list_temp.push({value: item.key, label: item.key}))
      setUsers_emails(list_temp)

      
    }
  }
  
  async function get_awards_list(){
    let response = await awardsAPI.view_awards();
    console.log(response)
    if (!response.was_exception){
      setAwards_list(response.value)  
      let temp = response.value
      let temp1 = temp.map((item) => {
        return (
          {key: item.id}
        );
      })
      let list_temp = []
      temp1.map((item) => list_temp.push({value: item.key, label: item.key}))
      setAwards_ids_list(list_temp)


    }      
  }

  useEffect(() => {
    get_emails_list();
    get_awards_list();
  }, {})

  
  const add_to_emails = (text) => {
    if (emails.includes(text)){
      const new_list = emails.filter(item => item !== text)
      setEmails(new_list)
    }
    else{
      setEmails(emails.concat(text))
    }
    
  }


  const add_award = async() => {
    if (emails == "" || award_to_add == ""){
      alert("Please Enter Details.")
    }
    else
    {
      let response = await awardsAPI.add_award(emails, award_to_add);
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Award has been successfully added to the system.");
        get_awards_list();

      }
    }
    
  }



  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Awards List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={awards_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <TextInput
        style={styles.textInputer}
        placeholder="award message"
        onChangeText={newText => setText_to_award_to_add(newText)}
      />
      <Select
        placeholder="emails to award"
        options={users_emails}
        onChange = {nextText => add_to_emails(nextText.value)}>
      </Select>
      <Button onPress={() => add_award()} title="Add Award" color="#841584"/>
      <Text><h3>Emails:</h3></Text>
      <Text>{emails}</Text>
      
    

    
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
    dataIndex: "date",
    key: "date",
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
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});




    




