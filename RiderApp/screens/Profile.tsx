
import EditScreenInfo from '../components/EditScreenInfo';
import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text, Button } from 'react-native';
import { RootTabScreenProps } from '../types';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigationContainer } from '@react-navigation/native';
import Navigation from '../navigation';

const Drawer = createDrawerNavigator();
export default function Profile  ({ navigation }: RootTabScreenProps<'Profile'>)  {
      const [email, setEmail] = useState('');
      const [password, setPassword] = useState('');
    
      const handleLogin = () => {
        // Handle login logic here
      };
    
      const handleForgotPassword = () => {
        // Handle forgot password logic here
      };
      return (
        <View style={styles.container}>
       <Button title='My Rides' onPress={()=>{navigation.navigate('MyRides')}} ></Button> 
       <Button title='Update Information' onPress={()=>{navigation.navigate('Profile')}} ></Button> 
       <Button title='My Messages' onPress={()=>{navigation.navigate('Profile')}} ></Button> 
       <Button title='Change Password' onPress={()=>{navigation.navigate('ChangePassword')}} ></Button> 
      <Text style={styles.title}>Profile</Text>
        </View>


      );

}

function SettingsScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>Settings Screen</Text>
    </View>
  );
}

function AboutScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>About Screen</Text>
    </View>
  );
}

function HelpScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>Help Screen</Text>
    </View>
  );
}

function SideNavigation() {
  return (
    <View style={{ flex: 1, backgroundColor: '#fff' }}>
      <View style={{ padding: 20 }}>
        <Text style={{ fontSize: 24, fontWeight: 'bold', marginBottom: 10 }}>Menu</Text>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Profile</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Settings</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>About</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Help</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
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
      backgroundColor: '#3498db',
      padding: 10,
      borderRadius: 5,
    },
    buttonText: {
      color: '#fff',
      fontSize: 18,
    },
  });