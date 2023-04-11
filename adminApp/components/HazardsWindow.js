import * as React from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';
import { HazardsApi } from '../API/HazardsApi';


const hazardsApi = new HazardsApi();

let hazards_list = []
let hazards_ids_list = []

let hazards_types_list = [{value: "pothole", label:"Pothole"}, {value: "PoleTree", label:"Pole Tree"}, {value: "RoadSign", label:"Road Sign"}]
let cities_list = [{value: "Tel Aviv", label:"Tel Aviv"}, {value: "Haifa", label:"Haifa"}, {value: "Beersheba", label:"Beersheba"},]

let lng = ""
let lat = ""
let city = ""
let type = ""
let size = ""

let hazard_to_remove = ""
let hazard_to_report = ""

export const get_hazards_list = async () => {
  let response = await hazardsApi.view_hazards();
  if (!response.was_exception){
    hazards_list = response.value
    hazards_ids_list = hazards_list.map((item) => {
      return (
        {value: item.id, label: item.id}
      );
    })
  }
}



export default function HazardsWindow({navigation}) {
  get_hazards_list();


  const setText_to_lng = (text) => {
    lng = text
  }

  const setText_to_lat = (text) => {
    lat = text
  }

  const setText_to_city = (text) => {
    city = text
  }

  const setText_to_type = (text) => {
    type = text
  }

  const setText_to_size = (text) => {
    size = text
  }

  const setText_to_remove_hazard = (text) => {
    hazard_to_remove = text
  }

  const setText_to_report_hazard = (text) => {
    hazard_to_report = text
  }

  const add_hazard = () => {
    hazardsApi.add_hazard(lng, lat, city, type, size)
    get_hazards_list();
  }

  const delete_hazard = () => {
    if (hazard_to_remove == "")
      {
        alert("Please choose Hazard")
      }
      else
      {
        advertismentsApi.delete_hazard(hazard_to_remove)
      }
    get_hazards_list();
  }
  // let hazard_to_remove = ""
  // let hazard_to_report = ""
  const report_hazard = () => {
    if (hazard_to_report == ""){
      alert("Please choose Hazard")
    }
    else{
      alert("Report to OR YARUK about hazard : "+hazard_to_report)
    }
    
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Hazards List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={hazards_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lng"
          onChangeText={newText => setText_to_lng(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lat"
          onChangeText={newText => setText_to_lat(newText)}
        />
        <Select
          placeholder="Hazard City"
          options={cities_list}
            onChange={newText => setText_to_city(newText.value)}
        ></Select>
        <Select
          placeholder="Hazard Type"
          options={hazards_types_list}
            onChange={newText => setText_to_type(newText.value)}
        ></Select>
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Size"
          onChangeText={newText => setText_to_size(newText)}
        />
      <Button onPress={() => add_hazard()} title="Add Hazard" color="#841584"/>


      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>

      <Select
        placeholder="Hazard ID to delete"
        options={hazards_ids_list}
        onChange={newText => setText_to_remove_hazard(newText.value)}
      ></Select>
      <Button onPress={() => delete_hazard()} title="Delete Hazard" color="#841584"/>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="Hazard ID to Report"
        options={hazards_ids_list}
        onChange={newText => setText_to_report_hazard(newText.value)}
      ></Select>
      <Button onPress={() => report_hazard()} title="Report 'OR Yaruk'" color="green"/>
    
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




const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
    width: 200,
  },
  {
    title: "Ride ID",
    dataIndex: "ride_id",
    key: "ride_id",
    width: 200,
  },
  {
    title: "Location",
    dataIndex: "location",
    key: "location",
    width: 200,
  },
  {
    title: "City",
    dataIndex: "city",
    key: "city",
    width:200,
  },
  {
    title: "Type",
    dataIndex: "type",
    key: "type",
    width:200,
  },
  {
    title: "Size",
    dataIndex: "size",
    key: "size",
    width:200,
  },
  {
    title: "Hazard Rate",
    dataIndex: "rate",
    key: "rate",
    width:200,
  },
];



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
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});