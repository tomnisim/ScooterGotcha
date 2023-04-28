import { useState } from "react";
import { useEffect } from 'react';
import { UserApi } from '../API/UserApi';
import * as React from 'react';
import { ScrollView, View, Text, Button, StyleSheet } from 'react-native';
import Table from 'rc-table';



const userApi = new UserApi();


export const setName = (name) =>{
    console.log(name)
}

export default function TaskBar({navigation}) {

    const [showTextBox, setShowTextBox] = useState(false);
    const [notifications_list, setNotifications_list] = useState([{_message:1},{_message:2},{_message:3},{_message:4}])
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [name, setName] = useState("Guest")

    const handleButtonClick = async () => {
        await get_notifications_list()
      setShowTextBox(!showTextBox);
    }
  
    const logout = async () => {
        setIsLoggedIn(!isLoggedIn);
        await userApi.logout()
        navigation.navigate("Login")
    }
    
    async function get_notifications_list(){
      let response = await userApi.view_notifications();
      if (!response.was_exception){
        setNotifications_list(response.value)
      }
    }
    useEffect(() => {
      get_notifications_list();
    }, [])

    const handleClick = () => {
        setIsLoggedIn(!isLoggedIn);
      };


    return (
      <div className="task-bar">
        <text>Hello {name},</text>
        <br></br>
        <button onClick={handleButtonClick}>Notifications</button>
        <button onClick={logout}>
            {isLoggedIn ? "Logout" : "Login"}
            </button>

         <View style={{display: 'flex', flexDirection:'column', width: 450}}>
        {showTextBox &&
                  <ScrollView style={styles.container}>
                  <Table columns={columns} data={notifications_list} tableLayout="auto"/>
                  </ScrollView>
         
      }
      </View>

      </div>
    );
  }
  
  const columns = [
    {
      title: " ",
      dataIndex: "_message",
      key: "_message",
      width: 200,
    },
  ];
  const styles = StyleSheet.create({
    container: {
      width:550,
      height:150,
      padding: 10,
      opacity:0.7,
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