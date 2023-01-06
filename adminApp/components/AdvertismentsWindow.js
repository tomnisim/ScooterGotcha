import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AdvertismentsApi } from '../API/AdvertismentsApi';

const advertismentsApi = new AdvertismentsApi();
let advertisments_list = []

let advertise_id_to_delete = ""

let final_date = ""
let owner = ""
let message = ""
let photo = ""
let url = ""

const get_advertisments_list = async () => {
  // todo: change 5 to advertisment id, change params to functions.
  let response = await advertismentsApi.view_advertisements(5);
  if (!response.was_exception){
    advertisments_list = response.value
  }
}
get_advertisments_list()

export default function AdvertismentsWindow({navigation}) {

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
    advertismentsApi.delete_advertisement(advertise_id_to_delete,6)
  }

  const add_advertisement = () => {
    final_date, owner, message, photo, url, 
    advertismentsApi.add_advertisement(final_date, owner, message, photo, url,6)
  }

  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Text style={{fontSize: 30, borderColor: "gray"}}><b>Advertisments List:</b></Text>
      <View style={styles.container}>
      {advertisments_list.map((item) => {
        return (
          <View>
            <Text style={styles.item}>{"id: "+ item.id +", start_date: "+ item.start_date +", final_date: "+
             item.final_date + " owner: "+ item.owner +", message: " + item.message + " photo: " + item.photo +
             ", url: " + item.url + " users_clicks: "+ item.users_clicks}</Text>
          </View>
          
        );
      })}
        <View style={{alignItems: 'center', justifyContent: 'center' }}>
        <TextInput
          style={{height: 40}}
          placeholder="enter Advertise due date"
          onChangeText={newText => setText_to_final_date(newText)}
        />
              <TextInput
          style={{height: 40}}
          placeholder="enter Advertise message"
          onChangeText={newText => setText_to_message(newText)}
        />
              <TextInput
          style={{height: 40}}
          placeholder="enter Advertise owner"
          onChangeText={newText => setText_to_owner(newText)}
        />
              <TextInput
          style={{height: 40}}
          placeholder="enter Advertise photo"
          onChangeText={newText => setText_to_photo(newText)}
        />
              <TextInput
          style={{height: 40}}
          placeholder="enter Advertise url"
          onChangeText={newText => setText_to_url(newText)}
        />




      <Button onPress={() => add_advertisement()} title="Add Advertise" color="#007fff"/>
      <TextInput
          style={{height: 40}}
          placeholder="Advertise id to delete"
          onChangeText={newText => setText_to_delete_advertise(newText)}
        />
      <Button onPress={() => delete_advertise()} title="Delete advertisment" color="#007fff"/>
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



    