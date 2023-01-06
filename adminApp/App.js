import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import UsersWindow from './components/UsersWindow'
import HazardsWindow from './components/HazardsWindow'
import RidesWindow from './components/RidesWindow'
import AdminsWindow from './components/AdminsWindow'
import StatisticsWindow from './components/StatisticsWindow'
import PrizesWindow from './components/PrizesWindow'
import AdvertismentsWindow from './components/AdvertismentsWindow'
import SystemSettingsWindow from './components/SystemSettingsWindow'
import QuestionsWindow from './components/QuestionsWindow'










const Stack = createNativeStackNavigator();


function HomeScreen({navigation}) {
  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
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



function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Users" component={UsersWindow} />
        <Stack.Screen name="Questions" component={QuestionsWindow} />
        <Stack.Screen name="Hazards" component={HazardsWindow} />
        <Stack.Screen name="Rides" component={RidesWindow} />
        <Stack.Screen name="Admins" component={AdminsWindow} />
        <Stack.Screen name="Statistics" component={StatisticsWindow} />
        <Stack.Screen name="Prizes" component={PrizesWindow} />
        <Stack.Screen name="Advertisements" component={AdvertismentsWindow} />
        <Stack.Screen name="SystemSettings" component={SystemSettingsWindow} />
        
        </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;

