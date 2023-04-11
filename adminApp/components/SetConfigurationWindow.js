import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView } from 'react-native';
import { SettingsApi } from '../API/SettingsApi';
import { background } from '../API/Path';


const settingsApi = new SettingsApi();

export default function SetConfigurationWindow({navigation}) {

  const [data, setData] = useState([])

      const set_rp_config = async () => {
      let response = await settingsApi.set_rp_config()
      if (!response.was_exception){
        setData(response.value)
      }
      else
      {
        alert("cant complete this.")
      }
    }

    const set_server_config = async () => {
      let response = await settingsApi.set_server_config()
      if (!response.was_exception){
        setData(response.value)
      }
      else
      {
        alert("cant complete this.")
      }
    }



return (
  <View>
  <ImageBackground source={background} resizeMode="cover">
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Configurations Window:</b></Text>
  <View style={{display: 'flex', flexDirection:'row'}}>
  <ScrollView style={styles.container}>
  <View style={{display: 'flex', flexDirection:'row'}}>

  <Text>                                                             </Text>
  <View style={{display: 'flex', flexDirection:'column'}}>
    <Text><h3>     Server:</h3></Text>
    <TextInput
          style={styles.textInputer}
          placeholder="Number Of Routes"
          onChangeText={newText => alert(newText)}
        />
      <TextInput
        style={styles.textInputer}
        placeholder="Minimum Password Length"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Maximum Password Length"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="System Email"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="System Email Password"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Or Yaruk Email"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Number Of Coordinates"
        onChangeText={newText => alert(newText)}
      />
    </View>
<Text>                                                                                                      </Text>

  <View style={{display: 'flex', flexDirection:'column'}}>
    <Text><h3>     Raspberry Pi:</h3></Text>
    <TextInput
          style={styles.textInputer}
          placeholder="Alert Type"
          onChangeText={newText => alert(newText)}
        />
      <TextInput
        style={styles.textInputer}
        placeholder="Minimum Distance Alert"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Duration"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Voice"
        onChangeText={newText => alert(newText)}
      />
      <TextInput
        style={styles.textInputer}
        placeholder="Verbal Words"
        onChangeText={newText => alert(newText)}
      />
    </View>
    </View>
  
  </ScrollView>
  <Text>    </Text>    
  <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Button onPress={() => set_server_config()} title="Set Server Configuration" color="#841584"/>
    <Text> </Text>
    <Button onPress={() => set_rp_config()} title="Set RP Configuration" color="#841584"/>
    <Text> </Text>
  
</View>
</View>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
<Text> </Text>
</ImageBackground>
</View>

);



}
const styles = StyleSheet.create({
container: {
  width:1200,
  height:500,
  padding: 10,
  opacity:0.5,
  backgroundColor:'white'
},
hairline: {
  backgroundColor: 'black',
  height: 2,
  width: 200
},
textInputer: {
  backgroundColor:'white',
  opacity:0.8,
  height: 40
},
item: {
  padding: 20,
  fontSize: 15,
  marginTop: 5,
}
});




  
