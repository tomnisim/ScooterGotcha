import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';
import { RidesApi } from '../API/RidesApi';

const ridesApi = new RidesApi();



export default function RidesWindow({navigation}) {
  const [rides_list, setRides_list] = useState([])

  async function get_rides_list(){
    let response = await ridesApi.view_rides();
    if (!response.was_exception){
      setRides_list(response.value)
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
      {/* <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lng"
          onChangeText={newText => setText_to_lng(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lat"
          onChangeText={newText => setText_to_lat(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard City"
          onChangeText={newText => setText_to_city(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Type"
          onChangeText={newText => setText_to_type(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Size"
          onChangeText={newText => setText_to_size(newText)}
        />
      <Button onPress={() => add_hazard()} title="Add Hazard" color="#841584"/>


      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="Hazard ID to delete"
        options={rides_ids_list}
        onChange={newText => setText_to_remove_hazard(newText.value)}
      ></Select>
      <Button onPress={() => delete_hazard()} title="Delete Hazard" color="#841584"/> */}
    
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