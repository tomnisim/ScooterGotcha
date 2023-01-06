import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { QuestionsApi } from '../API/QuestionsApi';


const questionsApi = new QuestionsApi();
let questions_list = []
let question_id_to_answer = ""
let admin_answer = ""
let message_to_send = ""

const get_questions_list = async () => {
    // todo: change 5 to admin id, change params to functions.
  let response = await questionsApi.view_all_open_questions(5);
  console.log(response)
  if (!response.was_exception){
    questions_list = response.value
  }
  console.log(questions_list)
    
}

get_questions_list()
console.log(questions_list)

export default function QuestionsWindow({navigation}) {

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
    questionsApi.answer_question(5,"ansewr", 5)
  }

  const send_message_to_all_users = () => {
    alert(message_to_send)
    questionsApi.send_message_to_all_users("message", 5)
  }




return (
  <View style={{alignItems: 'center', justifyContent: 'center' }}>
    <Text style={{fontSize: 30}}><b>Questions List:</b></Text>
    <View style={styles.container}>
    {questions_list.map((item) => {
      return (
        <View>
          <Text style={styles.item}>{"question id: "+ item.question_id + ", message_date:" + item.message_date + 
                  ", answer_date id: "+ item.answer_date + ", message:" + item.message +
                  ", answer: "+ item.answer + ", has_answer:" + item.has_answer +
                  ", sender email: "+ item.senderEmail + ", responder email:" + item.responderEmail}</Text>
        </View>
      );
    })}
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
    <TextInput
        style={{height: 40}}
        placeholder="question id to answer"
        onChangeText={newText => setText_to_question_id_to_answer(newText)}
      />
      <TextInput
        style={{height: 40}}
        placeholder="admin answer"
        onChangeText={newText => setText_to_admin_answer(newText)}
      />
    <Button onPress={() => answer_question()} title="Answer Question" color="#007fff"/>
    <TextInput
        style={{height: 40}}
        placeholder="send message to all users"
        onChangeText={newText => setText_to_message_to_send(newText)}
      />
    <Button onPress={() => send_message_to_all_users()} title="Send Message to all Users" color="#007fff"/>
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
item: {
  padding: 20,
  fontSize: 15,
  marginTop: 5,
}
});

    