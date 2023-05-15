

import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text, ImageBackground} from 'react-native';
import { background } from '../API/AppConstans';
import { UserApi } from '../API/UserApi';
import {setUser} from './Profile'
import {setName} from './taskBar'

const userApi = new UserApi();

const LoginScreen= ({ navigation }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    let response = await userApi.login(email, password)
    if (response.was_exception || response.was_exception == undefined){
        if (response.was_exception == undefined)
            alert("The System is Unavailable Right Now, Please come back later.")
        else
            alert(response.message)
    }
    else
    {
      setUser(response.value)
      setName(response.value.name)
      navigation.navigate('Home')
    }
  };

  const handleRegister = () => {
    navigation.navigate('Register')
  }

  const handleForgotPassword = async () => {
    if (email == ""){
      alert("Please Enter An Email")
      return
    }
    let response = await userApi.reset_password(email)
    if (response.was_exception){
      alert("There is No Such an Email.")
    }
    else{
      alert("Your Password Has Been Reset, Please Check Your Email.")
    }
    
    // Handle forgot password logic here
  };

  return (
    <View>
      <ImageBackground source={background} resizeMode="cover">
    <View style={styles.container}>
      <Text style={styles.title}>Hey! Please Login</Text>

      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        secureTextEntry={true}
        value={password}
        onChangeText={setPassword}
      />
      <TouchableOpacity style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Login</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={handleRegister}>
        <Text style={styles.buttonText}>Register</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={handleForgotPassword}>
        <Text style={styles.forgotPassword}>Forgot Password?</Text>
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
    backgroundColor: '#841584',
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
    color: '#841584',
    textDecorationLine: 'underline',
  },
  
  title: {
    color: '#841584',
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});

export default LoginScreen;
