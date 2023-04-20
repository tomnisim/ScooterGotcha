import React,{ useState } from 'react';
import { useEffect } from 'react';
import { View, Text, Button, ScrollView, StyleSheet, Image } from 'react-native';
import Table from 'rc-table';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import UsersWindow from './components/UsersWindow'
import HazardsWindow from './components/HazardsWindow'
import RidesWindow from './components/RidesWindow'
import AdminsWindow from './components/AdminsWindow'
import StatisticsWindow from './components/StatisticsWindow'
import AwardsWindow from './components/AwardsWindow'
import AdvertismentsWindow from './components/AdvertismentsWindow'
import SystemSettingsWindow from './components/SystemSettingsWindow'
import QuestionsWindow from './components/QuestionsWindow'
import HomeWindow from './components/HomeWindow'
import LoginWindow from './components/LoginWindow'
import VisualRouteWindow from './components/VisualRoute' 
import VisualHazardsWindow from './components/VisualHazards' 
import SetConfigurationWindow from './components/SetConfigurationWindow'
import { LoginApi } from './API/LoginApi';




const Stack = createNativeStackNavigator();
const loginApi = new LoginApi();


// const [notifications_list, setNotifications_list] = useState([])
let notifications_list = []
let hasNewNotifications = false
const setNotifications_list = (value) => {
    notifications_list = value
    hasNewNotifications = false
}

export async function get_notifications_list(){
  let response = await loginApi.view_notifications();
  if (!response.was_exception){
    setNotifications_list(response.value)
    if (response.value.length > 0)
      hasNewNotifications = true
  }
}

function App() {
  const [showTextBox, setShowTextBox] = useState(false);
  let hasNewNotifications = false

  const handleButtonClick = () => {
    setShowTextBox(!showTextBox);
  }

  

  // useEffect(() => {
  //   alert("NOW")
  //   get_notifications_list();
  // }, [])


  return (
    <View style={{flex:1}}>
          <View style={{display: 'flex', flexDirection:'column', width:80}}>
            {hasNewNotifications && 
            <ImageButton src="https://raw.githubusercontent.com/tomnisim/ScooterGotcha/main/adminApp/assets/bell.png"
              alt="Notifications" onClick={handleButtonClick} />}

              {!hasNewNotifications && 
              <ImageButton src="https://raw.githubusercontent.com/tomnisim/ScooterGotcha/main/adminApp/assets/bell.png"
              alt="Notifications" onClick={handleButtonClick} />}
            
        {showTextBox &&
          <ScrollView style={styles.container}>
            <Table columns={columns} data={notifications_list} tableLayout="auto"/>
          </ScrollView>
         
      }
      
      </View>
    <NavigationContainer>
      {/* <View style={{display: 'flex', flexDirection:'column', width: 450}}>
            <ImageButton src="C:/Users/amitm/Desktop/SemH/ScooterGotcha/adminApp/assets/bell.png" alt="Notifications" onClick={handleButtonClick} />
            
        {showTextBox &&
                  <ScrollView style={styles.container}>
                                      <Text style={{textAlign:'center', color:'#841584', backgroundColor:'white', opacity:0.8}}><h1>Notifications</h1></Text>
                  <Table columns={columns} data={notifications_list} tableLayout="auto"/>
                  </ScrollView>
         
      }
      </View> */}
      {/* </View> */}

      <Stack.Navigator>
        <Stack.Screen name="Login" component={LoginWindow} />
        <Stack.Screen name="Home" component={HomeWindow} />
        <Stack.Screen name="Users" component={UsersWindow} />
        <Stack.Screen name="Questions" component={QuestionsWindow} />
        <Stack.Screen name="Hazards" component={HazardsWindow} />
        <Stack.Screen name="Rides" component={RidesWindow} />
        <Stack.Screen name="Admins" component={AdminsWindow} />
        <Stack.Screen name="Statistics" component={StatisticsWindow} />
        <Stack.Screen name="Awards" component={AwardsWindow} />
        <Stack.Screen name="Advertisements" component={AdvertismentsWindow} />
        <Stack.Screen name="SystemSettings" component={SystemSettingsWindow} />
        <Stack.Screen name="VisualRoute" component={VisualRouteWindow} />
        <Stack.Screen name="VisualHazards" component={VisualHazardsWindow} />
        <Stack.Screen name="SetConfiguration" component={SetConfigurationWindow} />

        </Stack.Navigator>
    </NavigationContainer>

    </View>

  );
}
const styles = StyleSheet.create({
  container: {
    width:550,
    height:200,
    padding: 10,
    opacity:0.5,
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

const columns = [
  {
    title: " ",
    dataIndex: "_message",
    key: "_message",
    width: 200,
  },
];
export default App;


function ImageButton(props) {
  return (
    <button style={{alignSelf:'self-end'}} className="image-button" onClick={props.onClick}>
      <img src={props.src} style={{height:50, width:50, alignItems:'end', alignSelf:'end'}} alt={props.alt} />
      </button>
  );
}

