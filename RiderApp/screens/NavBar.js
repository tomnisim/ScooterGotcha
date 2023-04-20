
import React from 'react';
import { useEffect, useState } from 'react';
import {TouchableOpacity, Linking ,  ImageBackground,Image, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { background } from '../API/Paths';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Entypo from 'react-native-vector-icons/Entypo';
import Icon from 'react-native-vector-icons/FontAwesome';
import { useNavigation } from '@react-navigation/native';
import { Utils } from '../Utils/Utils';
import { UserApi } from '../API/UserApi';



export default function NavBar() {
    const utils = new Utils();
    const logout = async () => {
        let response = await userApi.logout();
        if (response.was_exception || response.was_exception == undefined){
            if (response.was_exception == undefined)
                alert("no connection")
            else{
                alert("failed to logout")
            }     
        }
        else
        {
            console.log(utils)
            utils.removeItem('connected_user')
            navigation.navigate('NavBar')
            navigation.navigate('Home')
            console.log(response.message)
        }
    }

const userApi = new UserApi();
    const [user, setUser] = useState(null);
    useEffect( () => {
        console.log("ggggg")
        const fetchData = async () => {
          const temp = await utils.getData('connected_user');
          setUser(temp);
        }
        fetchData();
    }, []);
    const navigation = useNavigation();
    console.log(user)
    const user_name = user==null ? "Guest" : user.firstName
  return (
    <View style={styles.container}>
       
        <TouchableOpacity style={styles.button} onPress={()=>{navigation.navigate('Home')}}>
            <Icon name="home" size={20} color="black" />
            {/* <Text style={styles.text}>{"home"}</Text> */}
        </TouchableOpacity>
        
      {/* <Image source={require('./home-icon.png')} style={styles.homeIcon} /> */}
      <Text style={styles.loginButton}>Hello {user_name}</Text>

      <View style={styles.menuItems}>
        <Text style={styles.menuItem}>Menu Item 1</Text>
        <Text style={styles.menuItem}>Menu Item 2</Text>
        <Text style={styles.menuItem}>Menu Item 3</Text>
      </View>
      {user == null ?
      <TouchableOpacity style={styles.button} onPress={()=>{navigation.navigate('Login')}}>
            {/* <Icon name="home" size={20} color="black" /> */}
            <Text style={styles.text}>{"Login"}</Text>
        </TouchableOpacity> : 
        <TouchableOpacity style={styles.button} onPress={()=>logout()}>
            {/* <Icon name="home" size={20} color="black" /> */}
            <Text style={styles.text}>{"Logout"}</Text>
        </TouchableOpacity>}
    </View>
  );
}


const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#ffffff',
        padding: 10,
        borderTopWidth: 1,
        borderBottomWidth: 1,
        borderColor: '#cccccc',
      },
      homeIcon: {
        height: 30,
      },
      menuItems: {
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
      },
      menuItem: {
        marginLeft: 10,
        marginRight: 10,
        padding: 8,
        borderRadius: 4,
        backgroundColor: '#4CAF50',
        color: '#ffffff',
        fontSize: 16,
      },
      loginButton: {
        padding: 8,
        borderRadius: 4,
        backgroundColor: '#4CAF50',
        color: '#ffffff',
        fontSize: 16,
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
        flexDirection: 'row',
        backgroundColor: 'blue',
        padding: 10,
        borderRadius: 5,
        alignItems: 'center',
        justifyContent: 'center',
      },
      text: {
        color: 'white',
        marginLeft: 10,
      },
    buttonText: {
      color: '#fff',
      fontWeight: 'bold',
    },
  });