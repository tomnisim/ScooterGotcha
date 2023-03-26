import * as React from 'react';
import { View, Text, Button, ImageBackground } from 'react-native';




export default function HomeWindow({navigation}) {
    return (
      <View >
        <Text><h1>Home Screen</h1></Text>
        <Button onPress={() => navigation.navigate('Users')} title="Users Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Admins')} title="Admins Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Advertisements')} title="Advertisements Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Questions')} title="Questions Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Hazards')} title="Hazards Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Rides')} title="Rides Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Statistics')} title="Statistics Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('Prizes')} title="Prizes Window" color="#841584"/>
        <Button onPress={() => navigation.navigate('SystemSettings')} title="System settings Window" color="#841584"/>
      </View>
    );
  }