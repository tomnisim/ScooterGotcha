import * as React from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatisticsApi } from '../API/StatisticsApi';
import { background } from '../API/Path';
import Table from 'rc-table';


const statsApi = new StatisticsApi();

let all_daily_data = []
let daily_data = []
let general_data = []


export const get_stats = async () => {
  let response = await statsApi.view_daily_statistics();
  if (!response.was_exception){
    daily_data.push(response.value)
    // daily_data = response.value
  }
  let response1 = await statsApi.view_general_statistics();
  console.log(response1)
  if (!response1.was_exception){
    console.log(response1)
    general_data.push(response1.value)
    // general_data = response1.value
  }
  let response2 = await statsApi.view_all_daily_statistics();
  if (!response2.was_exception){
    all_daily_data = response2.value
  }
}



// get_stats()
export default function StatisticsWindow({navigation}) {
  get_stats();

return (
  <View>
  <ImageBackground source={background} resizeMode="cover">
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>General Statistics:</b></Text>

  <View style={{display: 'flex', flexDirection:'column'}}> 
  <ScrollView style={styles.container}>
  <Table columns={general_columns} data={general_data} tableLayout="auto"/>
  </ScrollView>
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Daily Statistics:</b></Text>
  <ScrollView style={styles.container}>
  <Table columns={daily_columns} data={daily_data} tableLayout="auto"/>
  </ScrollView>
  </View>
  <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>All Daily Statistics:</b></Text>
  <ScrollView style={styles.container1}>
  <Table columns={daily_columns} data={all_daily_data} tableLayout="auto"/>
 </ScrollView> 
 
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

const general_columns = [
  {
    title: "Last Initial Time",
    dataIndex: "last_initial_time",
    key: "last_initial_time",
    width: 100,
  },
  {
    title: "Total Admins",
    dataIndex: "total_admins",
    key: "total_admins",
    width: 100,
  },
  {
    title: "Total Users",
    dataIndex: "total_users",
    key: "total_users",
    width: 100,
  },
  {
    title: "Total Awards",
    dataIndex: "total_awards",
    key: "total_awards",
    width: 100,
  },
  {
    title: "Total Rides",
    dataIndex: "total_rides",
    key: "total_rides",
    width: 100,
  },
  {
    title: "Total Users Questions",
    dataIndex: "total_users_questions",
    key: "total_users_questions",
    width: 100,
  },
  {
    title: "Total Admin Answers",
    dataIndex: "total_admin_answers",
    key: "total_admin_answers",
    width: 100,
  },
  {
    title: "Total Hazards",
    dataIndex: "total_hazards",
    key: "total_hazards",
    width: 100,
  },
  {
    title: "Total Advertisements",
    dataIndex: "total_advertisements",
    key: "total_advertisements",
    width: 100,
  },

];

const daily_columns = [
  {
    title: "Date",
    dataIndex: "date",
    key: "date",
    width: 100,
  },
  {
    title: "New Admins",
    dataIndex: "newAdmins",
    key: "newAdmins",
    width: 100,
  },
  {
    title: "Total Admins",
    dataIndex: "totalAdmins",
    key: "totalAdmins",
    width: 100,
  },
  {
    title: "New Users",
    dataIndex: "newUsers",
    key: "newUsers",
    width: 100,
  },
  {
    title: "Total Users",
    dataIndex: "totalUsers",
    key: "totalUsers",
    width: 100,
  },
  {
    title: "New Awards",
    dataIndex: "newAwards",
    key: "newAwards",
    width: 100,
  },
  {
    title: "Total Awards",
    dataIndex: "totalAwards",
    key: "totalAwards",
    width: 100,
  },
  {
    title: "New Rides",
    dataIndex: "newRides",
    key: "newRides",
    width: 100,
  },
  {
    title: "Total Rides",
    dataIndex: "totalRides",
    key: "totalRides",
    width: 100,
  },


  {
    title: "New Users Questions",
    dataIndex: "newUsersQuestions",
    key: "newUsersQuestions",
    width: 100,
  },
  {
    title: "Total Users Questions",
    dataIndex: "totalUsersQuestions",
    key: "totalUsersQuestions",
    width: 100,
  },
  {
    title: "New Admin Answers",
    dataIndex: "newAdminAnswers",
    key: "newAdminAnswers",
    width: 100,
  },
  {
    title: "Total Admin Answers",
    dataIndex: "totalAdminAnswers",
    key: "totalAdminAnswers",
    width: 100,
  },
  {
    title: "New Hazards",
    dataIndex: "newHazards",
    key: "newHazards",
    width: 100,
  },
  {
    title: "Total Hazards",
    dataIndex: "totalHazards",
    key: "totalHazards",
    width: 100,
  },
  {
    title: "New Advertisements",
    dataIndex: "newAdvertisements",
    key: "newAdvertisements",
    width: 100,
  },
  {
    title: "Total Advertisements",
    dataIndex: "totalAdvertisements",
    key: "totalAdvertisements",
    width: 100,
  },
  {
    title: "Login Events",
    dataIndex: "loginEvents",
    key: "loginEvents",
    width: 100,
  }, 
  {
    title: "Logout Events",
    dataIndex: "logoutEvents",
    key: "logoutEvents",
    width: 100,
  }, 
  {
    title: "Reset Events",
    dataIndex: "resetEvents",
    key: "resetEvents",
    width: 100,
  }, 
  {
    title: "Shut Down Events",
    dataIndex: "shutDownEvents",
    key: "shutDownEvents",
    width: 100,
  },  
];


const styles = StyleSheet.create({
  container: {
    width:1300,
    height:100,
    padding: 10,
    opacity:0.5,
    backgroundColor:'white'
  },
  container1: {
    width:1300,
    height:350,
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