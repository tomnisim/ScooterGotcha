

import React, { useState } from 'react';
import { UserApi } from '../API/UserApi';
import {ImageBackground, View, Text, Button, StyleSheet, TouchableOpacity, TextInput} from 'react-native';
import { background } from '../API/Paths';
const userApi = new UserApi();

const LoginScreen = ({navigation}) => {
  const [email, setEmail] = useState('tom@gmail.com');
  const [password, setPassword] = useState('Tn12345678');

  const login = async () => {
    let response = await userApi.login(email, password)
    if (response.was_exception || response.was_exception == undefined){
        if (response.was_exception == undefined)
            alert("no connection")
        else
            alert(response.message)
    }
    else
    {
        console.log(response.message)
        navigation.navigate('Home')
    }
  };



  return (
    <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Welcome! Please Log-In</h3></Text>
            <TextInput
          style={styles.input}
          placeholder="Enter Email"
          value={email}
          onChangeText={setEmail}
        />
      <TextInput
        style={styles.input}
        placeholder="Enter Password"
        value={password}
        onChangeText={setPassword}
      />
              <Button onPress={() => login()}  title="Login" color="#841584"/>
              <TouchableOpacity onPress={()=> navigation.navigate('Register')}>
                <Text  style={{ color: 'blue', textDecorationLine: 'underline', fontSize:30 }}>{'not registered yet? Please Register'}</Text>
              </TouchableOpacity>
            </View>
            </ImageBackground>
        </View>
      

      
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  input: {
    width: '80%',
    height: 50,
    padding: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderRadius: 5,
    backgroundColor:'white'
  },
  button: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
    marginTop: 10,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
  },
  forgotPassword: {
    marginTop: 10,
    color: '#3498db',
    textDecorationLine: 'underline',
  },
  
  title: {
    color: 'black',
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});

export default LoginScreen;
