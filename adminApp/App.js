import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import UsersWindow from './components/UsersWindow'
import HazardsWindow from './components/HazardsWindow'
import RidesWindow from './components/RidesWindow'
import AdminsWindow from './components/AdminsWindow'
import StatisticsWindow from './components/StatisticsWindow'
import AwardsWindow from './components/AwardsWindow'
import AdvertismentsWindow from './components/AdvertismentsWindow'
import SystemSettingsWindow from './components/SystemSettingsWindow'
import QuestionsWindow from './components/QuestionsWindow'
import HomeWindow from './components/HomeWindow'
import LoginWindow from './components/LoginWindow' 








const Stack = createNativeStackNavigator();


function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Login" component={LoginWindow} />
        <Stack.Screen name="Home" component={HomeWindow} />
        <Stack.Screen name="Users" component={UsersWindow} />
        <Stack.Screen name="Questions" component={QuestionsWindow} />
        <Stack.Screen name="Hazards" component={HazardsWindow} />
        <Stack.Screen name="Rides" component={RidesWindow} />
        <Stack.Screen name="Admins" component={AdminsWindow} />
        <Stack.Screen name="Statistics" component={StatisticsWindow} />
        <Stack.Screen name="Awards" component={AwardsWindow} />
        <Stack.Screen name="Advertisements" component={AdvertismentsWindow} />
        <Stack.Screen name="SystemSettings" component={SystemSettingsWindow} />
        </Stack.Navigator>
    </NavigationContainer>

  );
}

export default App;

