import React,{ useState } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
import {LoginApi } from '../API/LoginApi';
import { background } from '../API/Path';


const loginApi = new LoginApi();



export default function LoginWindow({navigation}) {
  // todo: change default
  const [user_email, setText_email] = useState("admin@gmail.com");
  const [user_password, setText_password] = useState("admin")

    const login = async () => {
        let response = await loginApi.login(user_email, user_password)
        console.log(response)
        if (response.was_exception || response.was_exception == undefined){
            if (response.was_exception == undefined)
                alert("No Connection, Please come back later.")
            else
                alert(response.message)
        }
        else
        {
            navigation.navigate('Home')
        }
        
    }

    return (
        
        <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Welcome to Gotcha Admin Application!</h3></Text>
            <TextInput
                style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
                placeholder="Enter Email"
                onChangeText={newText => setText_email(newText)}
                />
            <TextInput
                style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
                placeholder="Enter Password"
                onChangeText={newText => setText_password(newText)}
                />
              <Button onPress={() => login()}  title="Login" color="#841584"/>
            </View>
            </ImageBackground>
        </View>
      );
    }

    const styles = StyleSheet.create({
        image: {
            flex: 1,
            justifyContent: 'center',
          },
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
      
      
      
      
              
