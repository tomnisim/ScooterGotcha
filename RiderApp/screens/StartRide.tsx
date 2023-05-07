

import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text, ImageBackground } from 'react-native';
import { UserApi } from '../API/UserApi';
import VisualRouteScreen from './VisualRoute';
import { setRoute } from './VisualRoute';
import { background } from '../API/AppConstans';

const userApi = new UserApi();
let flag = true;

const StartRideScreen = ({navigation})  => {
  const [Origin, setOrigin] = useState('');
  const [Destination, setDestination] = useState('');
  const [RoutesData, setRoutesData] = useState('');

const get_current_location = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      const lat = position.coords.latitude;
      const lng = position.coords.longitude;
      const url = `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`;
      
      fetch(url)
        .then(response => response.json())
        .then(data => {
          const address = data.display_name;
          console.log(address);
          setOrigin(address)
        })
        .catch(error => console.log(error));
    });
  } else {
    console.log("Geolocation is not supported by this browser.");
  }
}
if (flag){
  flag = false;
  get_current_location();
  
}



  const handleStartRide = async () => {
    let response = await userApi.get_routes(Origin, Destination)
    if (response.was_exception || response.was_exception == null){
        console.log(response.value)
        alert("expection routes")
    }
    else
    {
        console.log(response.value)
        alert("nice")
        setRoute(response.value)
        navigation.navigate('VisualRoute');
      // setRoutesData(response.value)
    }

  };


  return (
    <View>
      <ImageBackground source={background} resizeMode="cover">
      <View style={styles.container}>
      <Text style={styles.title}>Lets Start Ride!</Text>

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
    backgroundColor: '#841584',
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
    color:'#841584',
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});

export default StartRideScreen;
