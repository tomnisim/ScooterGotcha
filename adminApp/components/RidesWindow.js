import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';
import { RidesApi } from '../API/RidesApi';
import {set_junctions} from './VisualRoute';


const ridesApi = new RidesApi();


export default function RidesWindow({navigation}) {
  const [rides_list, setRides_list] = useState([])
  const [rides_ids_list, setRides_ids_list] = useState([])
  const [ride_to_show, setRide_to_show] = useState("")

  const show_visual = async () => {
    if (ride_to_show == ""){
      alert("Please Chose a Ride.")
    }
    else
    {
      let foundItem = null;
      rides_list.forEach(function(value, key) {
      if (value.ride_id === ride_to_show) {
        foundItem = { key, value };
      }
    });
    let ride = foundItem.value;
  
     let junctions = [
        { id: 0, name: 'Origin', lat: ride.origin_lat, lng: ride.origin_lng},
        { id: 1, name: 'Destination', lat: ride.destination_lat, lng: ride.destination_lng},
      ];
      await set_junctions(junctions);
      navigation.navigate('VisualRoute');
    }


  }
  

  async function get_rides_list(){
    let response = await ridesApi.view_rides();
    console.log(response.value)
    if (!response.was_exception){
      setRides_list(response.value)
      let temp = response.value
      let temp1 = temp.map((item) => {
        return (
          {key: item.ride_id}
        );
      })
      let list_temp = []
      temp1.map((item) => list_temp.push({value: item.key, label: item.key}))
      setRides_ids_list(list_temp)
    }
  }

  useEffect(() => {
    get_rides_list();
  }, {})
  


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Rides List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={rides_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
    <Select
        placeholder="ride ID to show"
        options={rides_ids_list}
        onChange={newText => setRide_to_show(newText.value)}
      ></Select>
      <Button onPress={() => show_visual()}       
       title="Show Visual" color="#841584"/>
    
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
    dataIndex: "ride_id",
    key: "ride_id",
    width: 200,
  },
  {
    title: "Rider Email",
    dataIndex: "rider_email",
    key: "rider_email",
    width: 200,
  },
  {
    title: "Date",
    dataIndex: "date",
    key: "date",
    width: 200,
  },
  {
    title: "City",
    dataIndex: "city",
    key: "city",
    width:200,
  },
  {
    title: "Start Time",
    dataIndex: "start_time",
    key: "start_time",
    width:200,
  },
  {
    title: "End Time",
    dataIndex: "end_time",
    key: "end_time",
    width:200,
  },
  {
    title: "Origin",
    dataIndex: "origin",
    key: "origin",
    width:200,
  },
  {
    title: "Destination",
    dataIndex: "destination",
    key: "destination",
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