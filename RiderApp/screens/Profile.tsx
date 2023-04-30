
import EditScreenInfo from '../components/EditScreenInfo';
import React, { useState } from 'react';
import { ScrollView, StyleSheet, View, TextInput, TouchableOpacity, Text, Button, ImageBackground} from 'react-native';
import { background } from '../API/AppConstans';

// import { createDrawerNavigator } from '@react-navigation/drawer';
// import { NavigationContainer } from '@react-navigation/native';

// const Drawer = createDrawerNavigator()
let userData = ""

export const setUser = (user) => {
  console.log(user)
  userData = user
} 

export default function ProfileScreen  ({ navigation })  {
      const [email, setEmail] = useState(userData.userEmail);
      const [name, setName] = useState(userData.name);
      const [lastName, setLastName] = useState(userData.lastName);
      const [phone, setPhone] = useState(userData.phoneNumber);
      const [gender, setGender] = useState(userData.gender);
      const [birthDay, setBirthDay] = useState(userData.birthDay);
      const [raspberryPiSerialNumber, setRaspberryPiSerialNumber] = useState(userData.raspberryPiSerialNumber);
      const [licenceIssueDate, setLicenceIssueDate] = useState(userData.licenceIssueDate);
      const [scooterType, setScooterType] = useState(userData.scooterType);
      const [rating, setRating] = useState(userData.rating);



      return (
        <View>
        <ImageBackground source={background} resizeMode="cover">
        <View style={styles.container}>
          <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>                     Profile:</b></Text>
          <h2> </h2>
          <Text><b>Email: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={email}
            onChangeText={newText => setEmail(newText)}
          />
          <Text><b>Serial Number: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={raspberryPiSerialNumber}
            onChangeText={newText => setRaspberryPiSerialNumber(newText)}
          />
          
          <Text><b>Name: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={name}
            onChangeText={newText => setName(newText)}
          />
          <Text><b>Last Name: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={lastName}
            onChangeText={newText => setLastName(newText)}
          />
          <Text><b>Phone: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={phone}
            onChangeText={newText => setPhone(newText)}
          />
          <Text><b>Gender: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={gender}
            onChangeText={newText => setGender(newText)}
          />
          <Text><b>Birth Day: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={birthDay}
            onChangeText={newText => setBirthDay(newText)}
          />
          <Text><b>Licence Issue Date: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={licenceIssueDate}
            onChangeText={newText => setLicenceIssueDate(newText)}
          />
          <Text><b>Rating: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={rating}
            onChangeText={newText => setRating(newText)}
          />
          <Text><b>Scooter Type: </b></Text>
          <TextInput
            style={styles.textInputer}
            placeholder={scooterType}
            onChangeText={newText => setScooterType(newText)}
          />
        
          </View>
        <View style={{display: 'flex', alignItems:'center',paddingLeft:68, flexDirection:'row'}}>
       <Button title='Update Information' color={"#841584"} onPress={()=>{navigation.navigate('Profile')}} ></Button> 
       <h1>       </h1>
       <Button title='Change Password' onPress={()=>{navigation.navigate('ChangePassword')}} ></Button> 
       </View>
       </ImageBackground>

        </View>


      );
      

}

function SettingsScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>Settings Screen</Text>
    </View>
  );
}

function AboutScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>About Screen</Text>
    </View>
  );
}

function HelpScreen() {
  return (
    <View style={{ flex: 1 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold', padding: 20 }}>Help Screen</Text>
    </View>
  );
}

function SideNavigation() {
  return (
    <View style={{ flex: 1, backgroundColor: '#fff' }}>
      <View style={{ padding: 20 }}>
        <Text style={{ fontSize: 24, fontWeight: 'bold', marginBottom: 10 }}>Menu</Text>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Profile</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Settings</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>About</Text>
        </TouchableOpacity>
        <TouchableOpacity style={{ marginBottom: 10 }}>
          <Text style={{ fontWeight: 'bold' }}>Help</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}
const styles = StyleSheet.create({
    container: {
      width: 450,
      flex: 1,
      opacity:0.7,
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
      backgroundColor: '#841584',
      padding: 10,
      borderRadius: 5,
    },
    buttonText: {
      color: '#fff',
      fontSize: 18,
    },
    textInputer: {
      backgroundColor:'white',
      opacity:0.8,
      textAlign:'center',
      height: 40
    },
  });