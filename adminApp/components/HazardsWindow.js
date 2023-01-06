import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

export default function HazardsWindow({navigation}) {
  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Button onPress={() => navigation.navigate("Home")} title="Home" color="#000000"/>
      <Text>HazardsWindow Screen</Text>
    </View>
  );
};
    