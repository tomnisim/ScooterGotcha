import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView } from 'react-native';
import { SettingsApi } from '../API/SettingsApi';
import { background } from '../API/Path';
import Select from 'react-select'


const settingsApi = new SettingsApi();
const time_unit_options = [{value:"HOURS",label:"HOURS"},{value:"MINUTES",label:"MINUTES"},{value:"SECONDS",label:"SECONDS"}]
const number_of_routes_options = [{value:3,label:"3"},{value:4,label:"4"},{value:5,label:"5"},{value:6,label:"6"}]
const min_password_options = [{value:3,label:"3"},{value:4,label:"4"},{value:5,label:"5"},{value:6,label:"6"}]
const max_password_options = [{value:8,label:"8"},{value:12,label:"12"},{value:14,label:"14"},{value:16,label:"16"}]
const treshold_options = [{value:80,label:"80"},{value:85,label:"85"},{value:90,label:"90"},{value:95,label:"95"}]
const time_optinos = [{value:8,label:"8"},{value:12,label:"12"},{value:14,label:"14"},{value:16,label:"16"}]
const voice_options =  [{value:"MAN",label:"MAN"},{value:"WOMAN",label:"WOMAN"}]
const alert_options = [{value:"Vocal",label:"Vocal"},{value:"Verbal",label:"Verbal"},{value:"Visual",label:"Visual"}]
const words_options =  [{value:"Be Careful",label:"Be Careful"},{value:"Watch Out",label:"Watch Out"}]


export default function SetConfigurationWindow({navigation}) {

  const [data, setData] = useState({})

      const set_config = async () => {
      let response = await settingsApi.set_config()
      if (!response.was_exception){
        alert("System Configurations has been successfully Changed.");
        get_config();

      }
      else
      {
        alert("The system cant complete your request, please try again later.")
      }
    }

    async function get_config() {
      let response = await settingsApi.get_config()
      if (!response.was_exception){
        console.log(response.value)
        setData(response.value)
        console.log(data)
      }
      else
      {
        alert("cant complete this.")
      }
    }

    useEffect(() => {
      get_config();
    }, {})



return (
  <View>
  <ImageBackground source={background} resizeMode="cover">
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Configurations Window:</b></Text>
  <View style={{display: 'flex', flexDirection:'row'}}>
  <ScrollView style={styles.container}>
  <View style={{display: 'flex', flexDirection:'row'}}>

  <Text>                                 </Text>
  <View style={{display: 'flex', flexDirection:'column', width: 400}}>
    <Text><h3>     Server:</h3></Text>
        <Select
        placeholder="Number Of Routes"
        options={number_of_routes_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Minimum Password Length"
        options={min_password_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Maximum Password Length"
        options={max_password_options}
        onChange={newText => alert(newText.value)}
      ></Select>
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
      <Select
        placeholder="Number Of Coordinates"
        options={max_password_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Hazards Rate Treshold for Automatic Report"
        options={treshold_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Hazards Time To Report Automatically"
        options={time_optinos}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Hazards Time Unit To Report Automatically"
        options={time_unit_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Statistics Time To Update Automatically"
        options={time_optinos}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Statistics Time Unit To Update Automatically"
        options={time_unit_options}
        onChange={newText => alert(newText.value)}
      ></Select>
    </View>
    
<Text>                                                         </Text>

  <View style={{display: 'flex', flexDirection:'column'}}>
    <Text><h3>     Raspberry Pi:</h3></Text>
    <Select
        placeholder="Alert Type"
        options={alert_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Minimum Distance Alert [Meter]"
        options={min_password_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Duration"
        options={min_password_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Voice"
        options={voice_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      <Select
        placeholder="Verbal Words"
        options={words_options}
        onChange={newText => alert(newText.value)}
      ></Select>
      
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
  opacity:0.7,
  backgroundColor:'white'
},
hairline: {
  backgroundColor: 'black',
  height: 2,
  width: 200
},
textInputer: {
  backgroundColor:'white',
  opacity:1,
  height: 40
},
item: {
  padding: 20,
  fontSize: 15,
  marginTop: 5,
}
});




  
