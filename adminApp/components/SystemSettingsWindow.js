import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { SettingsApi } from '../API/SettingsApi';


const settingsApi = new SettingsApi();


export default function SystemSettingsWindow({navigation}) {

  const reset = () => {
    settingsApi.reset()
  }
  const shut_down = () => {
    settingsApi.shut_down()
  }
  const view_error_logger = () => {
    settingsApi.view_error_logger()
  }
  const view_system_logger = () => {
    settingsApi.view_system_logger()
  }
  const view_server_logger = () => {
    settingsApi.view_server_logger()
  }
  const set_rp_config = () => {
    settingsApi.set_rp_config()
  }
  const set_server_config = () => {
    settingsApi.set_server_config()
  }



  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
     <Text style={{fontSize: 30}}><b>System Settings Window:</b></Text>

    <Button onPress={() => set_server_config()} title="Set Server Configuration" color="#007fff"/>
    <Button onPress={() => set_rp_config()} title="Set RP Configuration" color="#007fff"/>
    <Button onPress={() => view_server_logger()} title="View Server Logger" color="#007fff"/>
    <Button onPress={() => view_system_logger()} title="View System Logger" color="#007fff"/>
    <Button onPress={() => view_error_logger()} title="View Error Logger" color="#007fff"/>
    <Button onPress={() => reset()} title="Reset" color="#007fff"/>
    <Button onPress={() => shut_down()} title="Shut Down" color="#007fff"/>
    </View>  
    
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 50,
  },
  hairline: {
    backgroundColor: '#A2A2A2',
    height: 2,
    width: 800
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
  });