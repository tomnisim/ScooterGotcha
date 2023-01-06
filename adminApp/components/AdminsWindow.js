import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AdminApi } from '../API/AdminApi';

const adminApi = new AdminApi();
let admins_list = []

let user_email_to_appoint = "none"
let user_email_to_remove_appoint = "none"

const get_admins_list = async () => {
  // todo: change 5 to admin id, change params to functions.
  let response = await adminApi.view_admins(5);
  if (!response.was_exception){
    admins_list = response.value
  }
}
get_admins_list()

export default function AdminsWindow({navigation}) {



  const setText_email_to_appoint = (text) => {
    user_email_to_appoint = text;
    }

  const setText_email_to_remove_appoint = (text) => {
    user_email_to_remove_appoint = text;
    }



  const add_admin = () => {
    alert(user_email_to_appoint);
    adminApi.add_admin("5","5","5","5","5","5");
    get_admins_list();
  }

  const delete_admin = () => {
    alert(user_email_to_remove_appoint)
    adminApi.delete_admin("6",6)
    get_admins_list()

  }


  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
        <TextInput
          style={{height: 40}}
          placeholder="User email to appoint"
          onChangeText={newText => setText_email_to_appoint(newText)}
        />
      <Button onPress={() => add_admin()} title="Add Admin" color="#007fff"/>
      <TextInput
          style={{height: 40}}
          placeholder="User email to delete appoint"
          onChangeText={newText => setText_email_to_remove_appoint(newText)}
        />
      <Button onPress={() => delete_admin()} title="Delete Admin" color="#007fff"/>
      <Text style={{fontSize: 30, borderColor: "gray"}}><b>Admins List:</b></Text>
      <View style={styles.container}>
      {admins_list.map((item) => {
        return (
          <View>
            <Text style={styles.item}>{"email: "+ item._email +", gender: "+ item._gender +", phone: "+ item._phone_number + 
            " appointed_by: "+ item._appointed_by +", appointment_date: "}</Text>
          </View>
        );
      })}
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




    