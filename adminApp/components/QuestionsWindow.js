import React,{ useState } from 'react';
import { useEffect } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { QuestionsApi } from '../API/QuestionsApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';


const questionsApi = new QuestionsApi();



export default function QuestionsWindow({navigation}) {

  const [questions_list, setQuestions_list] = useState([]);
  const [questions_ids_list, setQuestions_ids_list] = useState([]);
  const [question_id_to_answer, setText_to_question_id_to_answer] = useState('');
  const [admin_answer, setText_to_admin_answer] = useState('');
  const [message_to_send, setText_to_message_to_send] = useState('');


  async function get_questions_list(){
    let response = await questionsApi.view_all_open_questions();
    if (!response.was_exception){
      setQuestions_list(response.value)
      let temp = response.value
      let temp1 = temp.map((item) => {
        return (
          {key: item.question_id}
        );
      })
      let list_temp = []
      temp1.map((item) => list_temp.push({value: item.key, label: item.key}))
      setQuestions_ids_list(list_temp)
    }    
}

useEffect(() => {
  get_questions_list();
}, {})


  const answer_question = async () => {
    if (question_id_to_answer == "" || admin_answer == ""){
      alert("Please Enter Answer and Chose ID.")
    }
    else{
      let response = await questionsApi.answer_question(question_id_to_answer, admin_answer);
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Your Answer has been successfully added to the system.");
        get_questions_list();

      }
    }
    
  }

  const send_message_to_all_users = async () => {
    if (message_to_send == ""){
      alert("Please Enter A Message.")
    }
    else
    {
      let response = await questionsApi.send_message_to_all_users(message_to_send);
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Your Message has been successfully added to the system.");
        get_questions_list();

      }
    }
    
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Questions List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={questions_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Select
        placeholder="Question ID to answer"
        options={questions_ids_list}
        onChange={newText => setText_to_question_id_to_answer(newText.value)}
      ></Select>
      <TextInput
        style={styles.textInputer}
        placeholder="Admin Answer"
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
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});




    




