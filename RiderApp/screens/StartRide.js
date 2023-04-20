

import  { useState } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput, TouchableOpacity} from 'react-native';
import { background } from '../API/Paths';
import { UserApi } from '../API/UserApi';
const userApi = new UserApi();

export default function StartRideScreen({navigation}) {

  const [Origin, setOrigin] = useState('');
  const [Destination, setDestination] = useState('');
  const [RoutesData, setRoutesData] = useState('');

  const handleStartRide = async () => {
    let response = await userApi.get_routes()
    if (response.was_exception || response.was_exception == null){
        setRoutesData("null")
    }
    else
    {
      setRoutesData(response.value)
    }

  };


  return (
    <View style={{flex:0.75, padding:10}}>
    <ImageBackground source={background} resizeMode="cover" style={styles.image}>
    <View style={{alignItems: 'center'}}>
    <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Lets Start Ride!</h3></Text>
    <TextInput
        style={styles.input}
        placeholder="Origin"
        value={Origin}
        onChangeText={setOrigin}
      />
      <TextInput
        style={styles.input}
        placeholder="Destination"
        value={Destination}
        onChangeText={setDestination}
      />
      <TouchableOpacity style={styles.button} onPress={handleStartRide}>
        <Text style={styles.buttonText}>Get Routes</Text>
      </TouchableOpacity>
      <Text style={styles.title}>{RoutesData}</Text>
    </View>
    </ImageBackground>
</View>



  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  input: {
    backgroundColor:'white',
    width: '80%',
    height: 50,
    padding: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderRadius: 5,
  },
  button: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
    marginTop: 10,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
  },
  forgotDestination: {
    marginTop: 10,
    color: '#3498db',
    textDecorationLine: 'underline',
  },
  
  title: {
    color:'black',
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});

