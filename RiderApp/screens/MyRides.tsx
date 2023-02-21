import React from 'react';
import Navigation from '../navigation';
import { RootTabScreenProps } from '../types';

import { StyleSheet, View, TouchableOpacity, Text, Button } from 'react-native';
// const CenterButtonScreen = () => {
//     return (
//       <View style={styles.container}>
//         <TouchableOpacity style={styles.button}>
//           <Text style={styles.buttonText}>Click Me</Text>
//         </TouchableOpacity>
//       </View>
//     );
//   };
function MyRides  ({ navigation }: RootTabScreenProps<'MyRides'>)  {
    console.log(navigation)
  return (
    <View style={styles.container}>
       <Button title='My Profile' onPress={()=>{navigation.navigate('Profile')}} ></Button> 
       <Button title='Start Ride'></Button> 
       <Button title='Announcments'></Button> 
       <Button title='Contact Us'></Button> 
      <Text style={styles.title}>My Ridesss</Text>
    </View>
  );
};

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

export default MyRides;
