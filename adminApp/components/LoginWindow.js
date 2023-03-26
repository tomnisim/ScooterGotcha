import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { LoginApi } from '../API/LoginApi';



const loginApi = new LoginApi();
export let session_id = 0;



export default function LoginWindow({navigation}) {

    // todo: change details
    let user_email = "admin@gmail.com"
    let user_password = "12345678aA"

    const setText_email = (text) => {
        user_email = text;
        }

    const setText_password = (text) => {
        user_password = text;
        }


    const login = async () => {
        // todo : move only login success
        let response = await loginApi.login(user_email, user_password)
        console.log(response)
        if (response.was_exception || response.was_exception == undefined){
            if (response.was_exception == undefined)
                alert("no connection")
            else
                alert(response.message)
        }
        else
        {
            session_id = response.value;
            console.log(session_id);
            navigation.navigate('Home')
        }
        
    }

    return (
        <View style={{alignItems: 'center', justifyContent: 'center' }}>
            <Text><h1>Login Window</h1></Text>
            <TextInput
                style={{height: 40}}
                placeholder="User email"
                onChangeText={newText => setText_email(newText)}
                />
            <TextInput
                style={{height: 40}}
                placeholder="User password"
                onChangeText={newText => setText_password(newText)}
                />
            <Button onPress={() => login()} title="Login" color="#841584"/>
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
      
      
      
      
              