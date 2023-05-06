
import EditScreenInfo from '../components/EditScreenInfo';
import React, { useState } from 'react';
import { ScrollView, StyleSheet, View, TextInput, TouchableOpacity, Text, Button, ImageBackground} from 'react-native';
import { background } from '../API/AppConstans';
import { UserApi } from '../API/UserApi';

const usersApi = new UserApi();


export default function RegisterScreen  ({ navigation })  {
      const [email, setEmail] = useState('');
      const [password, setPassword] = useState('')
      const [name, setName] = useState('');
      const [lastName, setLastName] = useState('');
      const [phone, setPhone] = useState('');
      const [gender, setGender] = useState('');
      const [birthDay, setBirthDay] = useState('');
      const [raspberryPiSerialNumber, setRaspberryPiSerialNumber] = useState('');
      const [licenceIssueDate, setLicenceIssueDate] = useState('');
      const [scooterType, setScooterType] = useState('');

      async function handle_register(){
        let response = await usersApi.register(email, password, name, lastName, phone, gender, raspberryPiSerialNumber,
           licenceIssueDate, scooterType,birthDay)
        console.log(response)
        if (response.was_exception != undefined && !response.was_exception){
          // Do login

          alert("email:"+email)
          alert("password:"+password)
          let response1 = await usersApi.login(email, password)
          if (!response1.was_exception)
            navigation.navigate("Home")
        }
        else{
          alert("Invalid Serial number, Please Contact Gotcha Service representative.")
        }
      
      }
      

      return (
        <View>
        <ImageBackground source={background} resizeMode="cover">
        <View style={styles.container}>
          <h2> </h2>
          <Text><b>Email: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setEmail(newText)}
          />
          <Text><b>Password: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setPassword(newText)}
          />
          <Text><b>Serial Number: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setRaspberryPiSerialNumber(newText)}
          />
          
          <Text><b>Name: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setName(newText)}
          />
          <Text><b>Last Name: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setLastName(newText)}
          />
          <Text><b>Phone: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setPhone(newText)}
          />
          <Text><b>Gender: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setGender(newText)}
          />
          <Text><b>Birth Day: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setBirthDay(newText)}
          />
          <Text><b>Licence Issue Date: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder=""
            onChangeText={newText => setLicenceIssueDate(newText)}
          />
          <Text><b>Scooter Type: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={scooterType}
            onChangeText={newText => setScooterType(newText)}
          />
        
          </View>
        <View style={{display: 'flex', alignItems:'center',paddingLeft:68, flexDirection:'row'}}>
       <Button title='Register' color={"#841584"} onPress={()=>handle_register()} ></Button>  
       </View>
       </ImageBackground>

        </View>


      );
      

}


const styles = StyleSheet.create({
    container: {
      width: 450,
      flex: 1,
      opacity:0.7,
      backgroundColor: '#f5fcff',
    },
    title: {
      fontSize: 24,
      fontWeight: 'bold',
      marginBottom: 10,
    },
    description: {
      fontSize: 18,
      textAlign: 'center',
      marginHorizontal: 20,
    },
    button: {
      backgroundColor: '#841584',
      padding: 10,
      borderRadius: 5,
    },
    buttonText: {
      color: '#fff',
      fontSize: 18,
    },
    textInputer: {
      backgroundColor:'white',
      opacity:0.8,
      textAlign:'center',
      height: 40
    },
  });