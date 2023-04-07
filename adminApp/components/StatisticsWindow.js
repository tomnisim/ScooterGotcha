import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatisticsApi } from '../API/StatisticsApi';



const statsApi = new StatisticsApi();
let all_daily_data = []
let daily_data = []
let general_data = []


const get_stats = async () => {
  let response = await statsApi.view_daily_statistics();
  if (!response.was_exception){
    daily_data = response.value
  }
  let response1 = await statsApi.view_general_statistics();
  console.log(response1)
  if (!response1.was_exception){
    console.log(response1)
    general_data = response1.value
  }
  let response2 = await statsApi.view_all_daily_statistics();
  if (!response2.was_exception){
    all_daily_data = response2.value
  }
  console.log(daily_data)
  
  console.log(general_data)

  console.log(all_daily_data)
}

get_stats()

export default function StatisticsWindow({navigation}) {
  return (
      <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Text style={{fontSize: 30, borderColor: "gray"}}><b>System Statistics:</b></Text>
      <View style={styles.container}>
      <View>
          <Text style={styles.item}>{"init_system_time: "+ daily_data.init_system_time +
            "\n login_per_minutes: "+ daily_data.login_per_minutes +
            "\n logout_per_minutes: "+ daily_data.logout_per_minutes + 
            "\n connect_per_minutes: "+ daily_data.connect_per_minutes +
            "\n register_per_minutes: "+ daily_data.register_per_minutes +
            "\n num_of_online_users: "+ daily_data.num_of_online_users +
            "\n num_of_users: "+ daily_data.num_of_users}
            </Text>
          </View>
    </View>
    </View>

  );
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 50,
  },
  hairline: {
    backgroundColor: '#A2A2A2',
    height: 2,
    width: 800
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});