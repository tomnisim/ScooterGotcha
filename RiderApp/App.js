import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
// import UsersWindow from './components/UsersWindow'
import LoginScreen from './screens/Login';
import RegisterScreen from './screens/Register';
import HomeScreen from './screens/Home';
import ProfileScreen from './screens/Profile';
import MyRidesScreen from './screens/MyRides';
import StartRideScreen from './screens/StartRide';
import ChangePasswordScreen from './screens/ChangePassword';
import VisualRouteScreen from './screens/VisualRoute' 
import ContactUsScreen from './screens/ContactUs'

const Stack = createNativeStackNavigator();


function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Register" component={RegisterScreen} />
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Profile" component={ProfileScreen} />
        <Stack.Screen name="MyRides" component={MyRidesScreen} />
        <Stack.Screen name="Start Ride" component={StartRideScreen} />
        <Stack.Screen name="VisualRoute" component={VisualRouteScreen} />

        <Stack.Screen name="ContactUs" component={ContactUsScreen} />
        {/* <Stack.Screen name="Awards" component={AwardsWindow} />
        <Stack.Screen name="Advertisements" component={AdvertismentsWindow} /> */}
        <Stack.Screen name="ChangePassword" component={ChangePasswordScreen} />
        </Stack.Navigator>
    </NavigationContainer>

  );
}

export default App;

