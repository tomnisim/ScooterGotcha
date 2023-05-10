import React,{ useState } from 'react';
import { useEffect } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/AppConstans';
import { UserApi } from '../API/UserApi';

const questionsApi = new UserApi();



export default function ContactUsScreen({navigation}) {

  const [questions_list, setQuestions_list] = useState([]);
  const [message_to_send, setText_to_message_to_send] = useState('');


  async function get_questions_list(){
    let response = await questionsApi.view_questions();
    console.log(response)
    if (!response.was_exception){
      setQuestions_list(response.value)
    }    
}

    useEffect(() => {
    get_questions_list();
    }, {})



  const send_question = async () => {
    if (message_to_send == ""){
      alert("Please Enter A Message.")
    }
    else
    {
      let response = await questionsApi.add_question(message_to_send);
      if (response.was_exception){
        alert("The System Cant Complete Your Request, Please Try Again Later.")
      }
      else
      {
        alert("Your Message Has Been Successfully Sent To The System.");
        setText_to_message_to_send('')
        get_questions_list();

      }
    }
    
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Messages List:</b></Text>
    <View style={{display: 'flex', flexDirection:'column'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={questions_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{width:1000,alignItems: 'left', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      <TextInput
        style={styles.textInputer}
        placeholder="Your Message Here"
        onChangeText={newText => setText_to_message_to_send(newText)}
      />
    <View style={{width:600,alignItems: 'left', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Button onPress={() => send_question()} title="Contact Us" color="#841584"/></View>



    
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
    title: "Message",
    dataIndex: "message",
    key: "message",
    width:350,
  },
  {
    title: "Message Date",
    dataIndex: "message_date",
    key: "message_date",
    width: 150,
  },
  {
    title: "Answer",
    dataIndex: "answer",
    key: "answer",
    width:350,
  },
  {
    title: "Answer Date",
    dataIndex: "answer_date",
    key: "answer_date",
    width: 150,
  },

];



const styles = StyleSheet.create({
  container: {
    width:1000,
    height:350,
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
    height: 70,
    width:600,
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});




    




