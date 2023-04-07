import * as React from 'react';
import { View, Text, Button, ImageBackground } from 'react-native';
import { background } from '../API/Path';




export default function HomeWindow({navigation}) {
    return (
      <View>
        <ImageBackground source={background} resizeMode="cover">
        <Text style={{paddinRight:'300',right:100,marginRight:50, color:'#841584'}}><h1>     Home Screen</h1></Text>
        <View style={{width:750}}>
        <Button onPress={() => navigation.navigate('Users')} title="Users Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Admins')} title="Admins Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Advertisements')} title="Advertisements Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Questions')} title="Questions Window" title_color='black' color='#00000000'/>
        <Button onPress={() => navigation.navigate('Hazards')} title="Hazards Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Rides')} title="Rides Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Statistics')} title="Statistics Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Awards')} title="Awards Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('SystemSettings')} title="System settings Window" color='#00000000'/>
        </View>
        </ImageBackground>
      </View>
    );
  }