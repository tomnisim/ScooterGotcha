import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import Table from 'rc-table';
// import Select from 'react-select'
import { background } from '../API/Paths';
import { UserApi } from '../API/UserApi';

const userApi = new UserApi();



export default function MyQuestionsScreen({navigation}) {
  const [questions_list, setQuestions_list] = useState([])

  const get_questions_list = async () => {
    let response = await userApi.view_questions();
    if (response.was_exception || response.was_exception == undefined){
      if (response.was_exception == undefined)
          alert("no connection")
      else{

      }
          
  }
  else
  {
        setQuestions_list(response.value)
      console.log(response.message)

    }
    
      
    
  }

  useEffect(() => {
    get_questions_list();
  }, [])
  


  return (
    questions_list.length == 0 ? <Text style={styles.title}> No Questions To Show</Text> : 
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Questions List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table  columns={columns} data={questions_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      {/* <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lng"
          onChangeText={newText => setText_to_lng(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lat"
          onChangeText={newText => setText_to_lat(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard City"
          onChangeText={newText => setText_to_city(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Type"
          onChangeText={newText => setText_to_type(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Size"
          onChangeText={newText => setText_to_size(newText)}
        />
      <Button onPress={() => add_hazard()} title="Add Hazard" color="#841584"/>


      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="Hazard ID to delete"
        options={rides_ids_list}
        onChange={newText => setText_to_remove_hazard(newText.value)}
      ></Select>
      <Button onPress={() => delete_hazard()} title="Delete Hazard" color="#841584"/> */}
    
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


const styles = StyleSheet.create({
  columnSeparator: {
    borderRightWidth: 5,
    borderRightColor: '#ccc',
  },
  cell: {
    padding: 10,
    flex: 1,
  },
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
  title: {
    fontSize: 40,
    fontWeight: 'bold',
    marginBottom: 30,
    color: '#ff0000',
    flex: 1,
    justifyContent: 'center', // centers content vertically
    alignItems: 'center', // centers content horizontally
    textAlign: 'center', // centers text horizontally within the container
    textAlignVertical: 'center', // centers text vertically within the container
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


const columns = [
  {
    title: "Question id",
    dataIndex: "question_id",
    key: "question_id",
    width: 200,
   
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
    width:200,
    style: styles.columnSeparator,
  },
  {
    title: "Message Content",
    dataIndex: "Message_Content",
    key: "Message_Content",
    width:200,
  },
  {
    title: "Answer",
    dataIndex: "answer",
    key: "answer",
    width:200,
  },
  {
    title: "Responder Email",
    dataIndex: "responderEmail",
    key: "responderEmail",
    width:200,
  },
  {
    title: "Has Answer",
    dataIndex: "has_answer",
    key: "has_answer",
    width:200,
  },
];



