import * as React from 'react';
import { ImageBackground,View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AdminApi } from '../API/AdminApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';


const adminApi = new AdminApi();

let admins_list = []
let user_email_to_appoint = "none"
let password = ""
let phoneNumber = ""
let birthDay = ""
let gender = ""

let user_email_to_remove_appoint = "none"
let admins_emails = ""



export const get_admins_list = async () => {
  let response = await adminApi.view_admins();
  if (!response.was_exception){
    admins_list = response.value
    console.log(response.value)

    admins_emails = admins_list.map((item) => {
      return (
        {value: item._email, label: item._email}
      );
    })
    console.log(admins_emails)
  }
}






// get_admins_list()
export default function AdminsWindow({navigation}) {
  get_admins_list();


  const setText_email_to_appoint = (text) => {
    user_email_to_appoint = text;
    }
    const setText_password = (text) => {
      password = text;
      }
    const setText_phone = (text) => {
      phoneNumber = text;
    }
    const setText_gender = (text) => {
      gender = text;
      console.log(gender)
      console.log("gender changed")
      console.log(gender)
    }
    const setText_birthday = (text) => {
      birthDay = text;
    }
  

  const setText_email_to_remove_appoint = (text) => {
    user_email_to_remove_appoint = text;
    }



  const add_admin = () => {
    alert(user_email_to_appoint);
    adminApi.add_admin(user_email_to_appoint, password, phoneNumber, birthDay, gender);
    get_admins_list();
  }

  const delete_admin = () => {
    alert(user_email_to_remove_appoint)
    adminApi.delete_admin(user_email_to_remove_appoint)
    get_admins_list()

  }



  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Admins List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={admins_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    
    <TextInput
          style={styles.textInputer}
          placeholder="User email to appoint"
          onChangeText={newText => setText_email_to_appoint(newText)}
        />
      <TextInput
        style={styles.textInputer}
        placeholder="Password"
        onChangeText={newText => setText_password(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Phone"
        onChangeText={newText => setText_phone(newText)}
      />
      <Select
        placeholder='Gender'
        options={gender_options}
        onChange={newText => setText_gender(newText.value)}
      ></Select>
      <TextInput
        style={styles.textInputer}
        placeholder="Birth Day"
        onChangeText={newText => setText_birthday(newText)}
      />
      <Button onPress={() => add_admin()} title="Add Admin" color="#841584"/>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="User email to delete appoint"
        options={admins_emails}
        onChange={newText => setText_email_to_remove_appoint(newText.value)}
      ></Select>
      <Button onPress={() => delete_admin()} title="Delete Admin" color="#841584"/>
    
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
const gender_options = [
  {value: 'male', label: 'male'},
  {value: 'female', label: 'female'},
]
const columns = [
  {
    title: "Email",
    dataIndex: "_email",
    key: "_email",
    width: 200,
  },
  {
    title: "Gender",
    dataIndex: "_gender",
    key: "_gender",
    width: 200,
  },
  {
    title: "Phone",
    dataIndex: "_phone_number",
    key: "_phone_number",
    width: 200,
  },
  {
    title: "Appointed By",
    dataIndex: "_appointed_by",
    key: "_appointed_by",
    width:200,
  },
    {
    title: "Appointment Date",
    dataIndex: "_appointment_date",
    key: "_appointment_date",
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




    