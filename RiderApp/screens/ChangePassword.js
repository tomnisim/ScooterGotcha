import React,{ useState } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { background } from '../API/Paths';

export default function ChangePasswordScreen() {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');

  const change_password = async () => {
    // handle password change logic here
    console.log('currentPassword:', currentPassword);
    console.log('newPassword:', newPassword);
    console.log('confirmNewPassword:', confirmNewPassword);
    
      let response = await userApi.change_password(currentPassword, newPassword)
      if (response.was_exception || response.was_exception == undefined){
          if (response.was_exception == undefined)
              alert("no connection")
          else{
  
          }
              
      }
      else
      {
          console.log(response.message)
          navigation.navigate('Profile')
  
        }
    };


  return (
        
    <View style={{flex:0.75, padding:10}}>
        <ImageBackground source={background} resizeMode="cover" style={styles.image}>
        <View style={{alignItems: 'center'}}>
        <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Change Password</h3></Text>
        <TextInput
            style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
            placeholder="Current Password"
            onChangeText={newText => setCurrentPassword(newText)}
            />
        <TextInput
            style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
            placeholder="New Password"
            onChangeText={newText => setNewPassword(newText)}
            />
        <TextInput
            style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
            placeholder="Confirm New Password"
            onChangeText={newText => setConfirmNewPassword(newText)}
            />
          <Button onPress={() => change_password()}  title="Send" color="#841584"/>
        </View>
        </ImageBackground>
    </View>
  );

  
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    padding: 20,
  },
  inputContainer: {
    marginBottom: 20,
  },
  label: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
  },
  button: {
    backgroundColor: '#007bff',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
  },
});



