import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { AdvertismentsApi } from '../API/AdvertismentsApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';


const advertismentsApi = new AdvertismentsApi();



export default function AdvertismentsWindow({navigation}) {

const [advertisments_list, setAdvertisments_list] = useState([])
const [advs_ids_list, setAdvs_ids_list] = useState([])
const [advertise_id_to_delete, setText_to_delete_advertise] = useState('')
const [final_date, setText_to_final_date] = useState('')
const [owner, setText_to_owner] = useState('')
const [message, setText_to_message] = useState('')
const [photo, setText_to_photo] = useState('')
const [url, setText_to_url] = useState('')

async function get_advertisments_list(){
  let response = await advertismentsApi.view_advertisements();
  if (!response.was_exception){
    setAdvertisments_list(response.value)

    let temp = response.value
    let temp1 = temp.map((item) => {
      return (
        {key: item.id}
      );
    })
    let list_temp = []
    temp1.map((item) => list_temp.push({value: item.key, label: item.key}))
    setAdvs_ids_list(list_temp)
  }
}

  const delete_advertise = async () => {
    await advertismentsApi.delete_advertisement(advertise_id_to_delete)
    get_advertisments_list();
  }

  const add_advertisement = async () => {
    await advertismentsApi.add_advertisement(final_date, owner, message, photo, url)
    get_advertisments_list();
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Advertisments List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={advertisments_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      <TextInput
          style={styles.textInputer}
          placeholder="enter Advertise due date"
          onChangeText={newText => setText_to_final_date(newText)}
        />
      <TextInput
        style={styles.textInputer}
        placeholder="enter Advertise message"
        onChangeText={newText => setText_to_message(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="enter Advertise owner"
        onChangeText={newText => setText_to_owner(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="enter Advertise photo"
        onChangeText={newText => setText_to_photo(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="enter Advertise url"
          onChangeText={newText => setText_to_url(newText)}
      />
      <Button onPress={() => add_advertisement()} title="Add Advertise" color="#841584"/>

      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="Advertise id to delete"
        options={advs_ids_list}
        onChange={newText => setText_to_delete_advertise(newText.value)}
      ></Select>
      <Button onPress={() => delete_advertise()} title="Delete advertisment" color="#841584"/>
    
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
    title: "Start Date",
    dataIndex: "start_date",
    key: "start_date",
    width: 200,
  },
  {
    title: "Final Date",
    dataIndex: "final_date",
    key: "final_date",
    width: 200,
  },
  {
    title: "Owner",
    dataIndex: "owner",
    key: "owner",
    width:200,
  },
  {
    title: "URL",
    dataIndex: "url",
    key: "url",
    width:200,
  },
  {
    title: "Users Clicks",
    dataIndex: "users_clicks",
    key: "users_clicks",
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




    