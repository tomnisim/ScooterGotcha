import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView } from 'react-native';
import { SettingsApi } from '../API/SettingsApi';
import { background } from '../API/Path';


const settingsApi = new SettingsApi();

export default function SystemSettingsWindow({navigation}) {

  const [data, setData] = useState([])

  const reset = async () => {
    alert("Are You Sure?")
    let response = await settingsApi.reset()
    if (!response.was_exception){
      setData(response.value)
      alert("System Restart Successfully.")
    }
    else
    {
      alert("cant complete this.")
    }
  }

  const view_error_logger = async () => {
    let response = await settingsApi.view_error_logger()
    if (!response.was_exception){
      setData(response.value)
    }
    else
    {
      alert("cant complete this.")
    }
  }

  const view_server_logger = async () => {
    let response = await settingsApi.view_server_logger()
    if (!response.was_exception){
      setData(response.value)
    }
    else
    {
      alert("cant complete this.")
    }
  }

  const view_system_logger = async () => {
    let response = await settingsApi.view_system_logger()
    if (!response.was_exception){
      setData(response.value)
    }
    else
    {
      alert("cant complete this.")
    }
  }
    const shut_down = async () => {
      alert("Are You Sure?")
      let response = await settingsApi.shut_down()
      if (!response.was_exception){
        setData(response.value)
        alert("System Shut Down Successfully.")
      }
      else
      {
        alert("cant complete this.")
      }
    }

    const set_config = async () => {
      navigation.navigate('SetConfiguration');
    }




return (
  <View>
  <ImageBackground source={background} resizeMode="cover">
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>System Window:</b></Text>

  <View style={{display: 'flex', flexDirection:'row'}}>
  <ScrollView style={styles.container}>
  <Text>{data}</Text>
  </ScrollView>
  <Text>    </Text>    
  <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Button onPress={() => set_config()} title="Set System Configuration" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => view_server_logger()} title="View Server Logger" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => view_system_logger()} title="View System Logger" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => view_error_logger()} title="View Error Logger" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => reset()} title="Reset" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => shut_down()} title="Shut Down" color="#841584"/>
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
  height: 40
},
item: {
  padding: 20,
  fontSize: 15,
  marginTop: 5,
}
});




  
