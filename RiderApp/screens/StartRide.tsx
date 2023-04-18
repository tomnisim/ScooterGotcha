

import React, { useState } from 'react';
import { StyleSheet, View, TextInput, TouchableOpacity, Text } from 'react-native';
import { UserApi } from '../API/UserApi';

const userApi = new UserApi();

const StartRideScreen = () => {
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
        secureTextEntry={true}
        value={Destination}
        onChangeText={setDestination}
      />
      <TouchableOpacity style={styles.button} onPress={handleStartRide}>
        <Text style={styles.buttonText}>Get Routes</Text>
      </TouchableOpacity>
      <Text style={styles.title}>{RoutesData}</Text>
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

export default StartRideScreen;
