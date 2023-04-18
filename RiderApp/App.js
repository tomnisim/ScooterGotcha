import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// import { StatusBar } from 'expo-status-bar';
// import { SafeAreaProvider } from 'react-native-safe-area-context';
// import useCachedResources from './hooks/useCachedResources';
// import useColorScheme from './hooks/useColorScheme';
// import Navigation from './navigation';
import HomeScreen from './screens/Home';
import LoginScreen from './screens/Login';
import RegisterScreen from './screens/Register';
import ProfileScreen from './screens/Profile';
import ChangePasswordScreen from './screens/ChangePassword';
//   function Navigation({ navigation }: RootTabScreenProps<'Home'>) {
//   return (
//     <NavigationContainer
    
//       linking={LinkingConfiguration}
//       theme={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
//       <View style={styles.container}>
//       <Button title="LogIn" style={styles.button} onPress={()=>navigation.navigate('Login')} />
//       <Button title="Button 2" style={styles.button} />
//       <Button title="Button 3" style={styles.button} />
//     </View>
      
//       <RootNavigator />
      
//     </NavigationContainer>
//   );
// }
const Stack = createNativeStackNavigator();
export default function App() {
  // const isLoadingComplete = useCachedResources();
  // const colorScheme = useColorScheme();

  // if (!isLoadingComplete) {
  //   alert("NULL")
  //   return null;
  // } else {
    return (
      // <Navigation> </Navigation>
      <NavigationContainer>
      <Stack.Navigator initialRouteName={"Login"}>
        
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Register" component={RegisterScreen} />
        <Stack.Screen name="Profile" component={ProfileScreen} />
        <Stack.Screen name="ChangePassword" component={ChangePasswordScreen} />
        
        {/* <Stack.Screen name="Users" component={UsersWindow} />
        <Stack.Screen name="Questions" component={QuestionsWindow} />
        <Stack.Screen name="Hazards" component={HazardsWindow} />
        <Stack.Screen name="Rides" component={RidesWindow} />
        <Stack.Screen name="Admins" component={AdminsWindow} />
        <Stack.Screen name="Statistics" component={StatisticsWindow} />
        <Stack.Screen name="Awards" component={AwardsWindow} />
        <Stack.Screen name="Advertisements" component={AdvertismentsWindow} />
        <Stack.Screen name="SystemSettings" component={SystemSettingsWindow} />
        <Stack.Screen name="VisualRoute" component={VisualRouteWindow} /> */}
        </Stack.Navigator>
    </NavigationContainer>
    );
  // }
}
