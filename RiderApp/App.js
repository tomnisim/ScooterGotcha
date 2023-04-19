import * as React from 'react';
import { View, Text, Button, ColorSchemeName } from 'react-native';
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
import MyRidesScreen from './screens/MyRides';
import MyQuestionsScreen from './screens/MyQuestions';
// import linking from  './navigation/LinkingConfiguration';
import Navigator from './screens/NavBar';
import LinkingConfiguration from './navigation/LinkingConfiguration';  
// import { StyleSheet, Button, ColorSchemeName, Pressable, View } from 'react-native';
import Navigation from './navigation';
import NavBar from './screens/NavBar';
// function Navigation({ navigation, colorScheme }) {
//   return (
//     <NavigationContainer
    
//       linking={LinkingConfiguration}
//       theme={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
//       <View style={styles.container}>
//       {/* <Button title="LogIn" style={styles.button} onPress={()=>navigation.navigate('Login')} /> */}
//       <Button title="Button 2" style={styles.button} />
//       <Button title="Button 3" style={styles.button} />
//     </View>
      
//       <RootNavigator />
      
//     </NavigationContainer>
//   );
// }
const Stack = createNativeStackNavigator();
export default function App({navigation}) {
  // const isLoadingComplete = useCachedResources();
  // const colorScheme = useColorScheme();

  // if (!isLoadingComplete) {
  //   alert("NULL")
  //   return null;
  // } else {
    
  

    return (
      // <Navigation></Navigation>,
      <NavigationContainer>
       <NavBar></NavBar> 
        {/* <Navigator ></Navigator> */}
      <Stack.Navigator initialRouteName={"Login"}>
        
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Register" component={RegisterScreen} />
        <Stack.Screen name="Profile" component={ProfileScreen} />
        <Stack.Screen name="ChangePassword" component={ChangePasswordScreen} />
        <Stack.Screen name="MyRides" component={MyRidesScreen} />
        <Stack.Screen name="Questions" component={MyQuestionsScreen} />
        <Stack.Screen name="NavBar" component={NavBar} />
        

        </Stack.Navigator>
    </NavigationContainer>
    );
  // }
}


// const styles = StyleSheet.create({
//   container: {
//     flexDirection: 'row',
//     backgroundColor: '#fff',
//     padding: 20,
//   },
//   inputContainer: {
//     marginBottom: 20,
//   },
//   label: {
//     fontWeight: 'bold',
//     marginBottom: 5,
//   },
//   input: {
//     borderWidth: 1,
//     borderColor: '#ccc',
//     padding: 10,
//   },
//   button: {
//     backgroundColor: '#007bff',
//     padding: 10,
//     borderRadius: 5,
//     alignItems: 'center',
//   },
//   buttonText: {
//     color: '#fff',
//     fontWeight: 'bold',
//   },
// });