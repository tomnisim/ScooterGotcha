import * as React from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { QuestionsApi } from '../API/QuestionsApi';
import Table from 'rc-table';
import Select from 'react-select'


const background = {uri: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/adminAppDesign/adminApp/assets/background.png'};

const questionsApi = new QuestionsApi();
let questions_list = []
let question_id_to_answer = ""
let admin_answer = ""
let message_to_send = ""

let questions_ids_list = []


const get_questions_list = async () => {
    // todo: change 5 to admin id, change params to functions.
  let response = await questionsApi.view_all_open_questions();
  console.log(response)
  if (!response.was_exception){
    questions_list = response.value


    questions_ids_list = questions_list.map((item) => {
      return (
        {value: item.question_id, label: item.question_id}
      );
    })
  }
  console.log(questions_list)
    
}


get_questions_list();
export default function QuestionsWindow({navigation}) {
  get_questions_list()
  console.log(questions_list)

  const setText_to_question_id_to_answer = (text) => {
    question_id_to_answer = text
  }
  const setText_to_admin_answer = (text) => {
    admin_answer = text
  }
  const setText_to_message_to_send = (text) => {
    message_to_send = text
  }

  const answer_question = () => {
    alert(question_id_to_answer)
    alert(admin_answer)
    questionsApi.answer_question(question_id_to_answer, admin_answer)
  }

  const send_message_to_all_users = () => {
    alert(message_to_send)
    questionsApi.send_message_to_all_users(message_to_send)
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Questions List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <View style={styles.container}>
    <Table columns={columns} data={questions_list} tableLayout="auto"/>
    </View>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Select
        placeholder="question id to answer"
        options={questions_ids_list}
        onChange={newText => setText_to_question_id_to_answer(newText.value)}
      ></Select>
      <TextInput
        style={styles.textInputer}
        placeholder="admin answer"
        onChangeText={newText => setText_to_admin_answer(newText)}
      />
    <Button onPress={() => answer_question()} title="Answer Question" color="#841584"/>

      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <TextInput
        style={styles.textInputer}
        placeholder="send message to all users"
        onChangeText={newText => setText_to_message_to_send(newText)}
      />
    <Button onPress={() => send_message_to_all_users()} title="Send Message to all Users" color="#841584"/>



    
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
    dataIndex: "question_id",
    key: "question_id",
    width: 200,
  },
  {
    title: "sender email",
    dataIndex: "senderEmail",
    key: "senderEmail",
    width:200,
  },
  {
    title: "Message",
    dataIndex: "message",
    key: "message",
    width:200,
  },
  {
    title: "Message Date",
    dataIndex: "message_date",
    key: "message_date",
    width: 200,
  },
  {
    title: "Answer Date",
    dataIndex: "answer_date",
    key: "answer_date",
    width: 200,
  },
  {
    title: "Has Answer",
    dataIndex: "_has_answer",
    key: "_has_answer",
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




    




