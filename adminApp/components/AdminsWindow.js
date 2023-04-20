import React,{ useState } from 'react';
import { ImageBackground,View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { AdminApi } from '../API/AdminApi';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';
import { useEffect } from 'react';


const adminApi = new AdminApi();

export default function AdminsWindow({navigation}) {

  const [admins_emails, setAdmins_emails] = useState([]);
  const [admins_list, setAdmins_list] = useState('');
  const [user_email_to_appoint, setEmail_to_appoint] = useState('');
  const [password, setPassword] = useState('');
  const [phoneNumber, setPhone] = useState('');
  const [birthDay, setBirthday] = useState('');
  const [gender, setGender] = useState('');
  const [user_email_to_remove_appoint, setEmail_to_remove_appoint] = useState('')

  async function get_admins_list(){
    let response = await adminApi.view_admins();
    if (!response.was_exception){
      if (Array.isArray(response.value)){
        setAdmins_list(response.value)
        let temp = response.value
        let users_emails = temp.map((item) => {
          return (
            {key: item.userEmail}
          );
        })
        let list_temp = []
        users_emails.map((item) => list_temp.push({value: item.key, label: item.key}))
        setAdmins_emails(list_temp)
      }
    }
  }

  useEffect(() => {
    get_admins_list();
  }, {})


  const add_admin = async () => {
    if (user_email_to_appoint == "" || password == "" || phoneNumber == "" || birthDay == "" || gender == "")
    {
      alert("Please Enter Admin Details.")
    }
    else
    {
      let response = await adminApi.add_admin(user_email_to_appoint, password, phoneNumber, birthDay, gender);
      if (!response.was_exception)
      {
        alert("Admin has been successfully added to the system.")
        get_admins_list();

      }
      else
      {
        alert("The system cant complete your request, please try again later.")
      }

    }
  }

  const delete_admin = async () => {
    if (user_email_to_remove_appoint == ""){
      alert("Please Enter Admin Email.")
    }
    else
    {
      let response = await adminApi.delete_admin(user_email_to_remove_appoint);
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Admin has been successfully deleted from the system.")
        get_admins_list();

      }
    }
    
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
          onChangeText={newText => setEmail_to_appoint(newText)}
        />
      <TextInput
        style={styles.textInputer}
        placeholder="Password"
        onChangeText={newText => setPassword(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Phone"
        onChangeText={newText => setPhone(newText)}
      />
      <Select
        placeholder='Gender'
        options={gender_options}
        onChange={newText => setGender(newText.value)}
      ></Select>
      <TextInput
        style={styles.textInputer}
        placeholder="Birth Day"
        onChangeText={newText => setBirthday(newText)}
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
        onChange={newText => setEmail_to_remove_appoint(newText.value)}
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
    dataIndex: "userEmail",
    key: "userEmail",
    width: 200,
  },
  {
    title: "Name",
    dataIndex: "name",
    key: "name",
    width: 200,
  },
  {
    title: "Last Name",
    dataIndex: "lastName",
    key: "namlastName",
    width: 200,
  },
  {
    title: "Gender",
    dataIndex: "gender",
    key: "gender",
    width: 200,
  },
  {
    title: "Phone",
    dataIndex: "phoneNumber",
    key: "phoneNumber",
    width: 200,
  },
  {
    title: "Appointed By",
    dataIndex: "appointedBy",
    key: "appointedBy",
    width:200,
  },
    {
    title: "Appointment Date",
    dataIndex: "appointmentDate",
    key: "appointmentDate",
    width:200,
  },
  {
    title: "Online",
    dataIndex: "loggedIn",
    key: "loggedIn",
    width: 200,
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




    