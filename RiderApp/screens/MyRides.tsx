import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView, TouchableOpacity} from 'react-native';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/AppConstans';
import { UserApi } from '../API/UserApi';
import { setRide } from './VisualRide';

const ridesApi = new UserApi();



export default function MyRidesScreen({navigation}) {
  const [rides_list, setRides_list] = useState([])

  async function get_rides_list(){
    let response = await ridesApi.view_rides();
    if (!response.was_exception){
      setRides_list(response.value)
      console.log(response.value)
    }
  }

  const columns = [

    {
      title: "Date",
      dataIndex: "date",
      key: "date",
      width: 200,
    },
    {
      title: "Origin",
      dataIndex: "originAddress",
      key: "originAddress",
      width:200,
    },
    {
      title: "Destination",
      dataIndex: "destinationAddress",
      key: "destinationAddress",
      width:200,
    },
    {
      title: "Start Time",
      dataIndex: "start_time",
      key: "start_time",
      width: 200,
      render: (startTime) => {
        const hhmm = convertToHHMM(startTime);
        return <span>{hhmm}</span>;
      },
    },
    {
      title: "Duration [HH:MM]",
      dataIndex: "duration",
      key: "duration",
      width: 200,
      render: (duration) => {
        const formattedDuration = parseFloat(duration).toFixed(2);
        return <span>{formattedDuration}</span>;
      },
    },
    {
      title: "Distance [Km]",
      dataIndex: "distance",
      key: "distance",
      width: 200,
      render: (distance) => {
        const formattedDistance = formatMetersToKM(distance);
        return <span>{formattedDistance}</span>;
      },
    },
    {
      title: "Show Visual",
      dataIndex: "",
      key: "Show",
      render: (text, row) => (
        <TouchableOpacity onPress={() => handleVisualShow(row)} style={{color: 'blue'}}>
          Show
        </TouchableOpacity>      ),
    },
  ];
  
  useEffect(() => {
    get_rides_list();
  }, [])
  
  const handleVisualShow = (row) => {
    setRide(row)
    navigation.navigate('VisualRide');
  }

  function convertToHHMM(dateTimeStr) {
    const dateTime = new Date(dateTimeStr);
    const hours = String(dateTime.getHours()).padStart(2, '0');
    const minutes = String(dateTime.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
  }

  function formatMetersToKM(meters) {
    const kilometers = meters / 1000;
    return kilometers.toFixed(3);
  }


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Rides List:</b></Text>

    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={rides_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center', borderEndColor:'black', borderColor:'black' }}>
   
  </View>
  
  </View>

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
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});