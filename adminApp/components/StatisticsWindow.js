import * as React from 'react';
import { View, Text, Button, StyleSheet, TextInput} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatisticsApi } from '../API/StatisticsApi';



const statsApi = new StatisticsApi();
let data = []

const get_stats = async () => {
  let response = await statsApi.view_statistics();
  if (!response.was_exception){
    data = response.value
  }
}
get_stats()

export default function StatisticsWindow({navigation}) {
  console.log(data)
  return (
      <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Text style={{fontSize: 30, borderColor: "gray"}}><b>System Statistics:</b></Text>
      <View style={styles.container}>
      <View>
          <Text style={styles.item}>{"init_system_time: "+ data.init_system_time +
            "\n login_per_minutes: "+ data.login_per_minutes +
            "\n logout_per_minutes: "+ data.logout_per_minutes + 
            "\n connect_per_minutes: "+ data.connect_per_minutes +
            "\n register_per_minutes: "+ data.register_per_minutes +
            "\n num_of_online_users: "+ data.num_of_online_users +
            "\n num_of_users: "+ data.num_of_users}
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




// import React, { Component } from 'react';
// import CanvasJSReact from './canvasjs.react';
// var CanvasJS = CanvasJSReact.CanvasJS;
// var CanvasJSChart = CanvasJSReact.CanvasJSChart;
 
// class StatisticsWindow extends Component {
// render() {
//   const options = {
//     animationEnabled: true,
//     exportEnabled: true,
//     theme: "light2", //"light1", "dark1", "dark2"
//     title:{
//       text: "Simple Column Chart with Index Labels"
//     },
//     axisY: {
//       includeZero: true
//     },
//     data: [{
//       type: "column", //change type to bar, line, area, pie, etc
//       //indexLabel: "{y}", //Shows y value on all Data Points
//       indexLabelFontColor: "#5A5757",
//       indexLabelPlacement: "outside",
//       dataPoints: [
//         { x: 10, y: 71 },
//         { x: 20, y: 55 },
//         { x: 30, y: 50 },
//         { x: 40, y: 65 },
//         { x: 50, y: 71 },
//         { x: 60, y: 68 },
//         { x: 70, y: 38 },
//         { x: 80, y: 92, indexLabel: "Highest" },
//         { x: 90, y: 54 },
//         { x: 100, y: 60 },
//         { x: 110, y: 21 },
//         { x: 120, y: 49 },
//         { x: 130, y: 36 }
//       ]
//     }]
//   }
  
//   return (
//   <div>
//     <CanvasJSChart options = {options} 
//       /* onRef={ref => this.chart = ref} */
//     />
//     {/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
//   </div>
//   );
// }
// }

// module.exports = StatisticsWindow;