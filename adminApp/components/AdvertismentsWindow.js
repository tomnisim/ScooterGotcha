import * as React from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AdvertismentsApi } from '../API/AdvertismentsApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';



const advertismentsApi = new AdvertismentsApi();

let advertisments_list = []
let advs_ids_list = []
let advertise_id_to_delete = ""
let final_date = ""
let owner = ""
let message = ""
let photo = ""
let url = ""

export const get_advertisments_list = async () => {
  let response = await advertismentsApi.view_advertisements();
  if (!response.was_exception){
    advertisments_list = response.value

    advs_ids_list = advertisments_list.map((item) => {
      return (
        {value: item.id, label: item.id}
      );
    })
  }
}

// get_advertisments_list()
export default function AdvertismentsWindow({navigation}) {
  get_advertisments_list()

  const setText_to_final_date = (text) => {
    final_date = text
  }

  const setText_to_owner = (text) => {
    owner = text
  }

  const setText_to_message = (text) => {
    message = text
  }

  const setText_to_photo = (text) => {
    photo = text
  }

  const setText_to_url = (text) => {
    url = text
  }

  const setText_to_delete_advertise = (text) => {
    advertise_id_to_delete = text
  }

  const delete_advertise = () => {
    alert(advertise_id_to_delete)
    advertismentsApi.delete_advertisement(advertise_id_to_delete)
    get_advertisments_list();
  }

  const add_advertisement = () => {
    final_date, owner, message, photo, url, 
    advertismentsApi.add_advertisement(final_date, owner, message, photo, url)
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
          onChangeText={newText => setText_to_url(newText.value)}
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
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});




    