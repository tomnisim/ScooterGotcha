import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ScrollView ,View, Text, Button, ImageBackground, StyleSheet} from 'react-native';
import Table from 'rc-table';
import { background } from '../API/Path';
import { LoginApi } from '../API/LoginApi';



const loginApi = new LoginApi();


export default function HomeWindow({navigation}) {
  const [showTextBox, setShowTextBox] = useState(false);

  const handleButtonClick = () => {
    setShowTextBox(!showTextBox);
  }

  const [notifications_list, setNotifications_list] = useState([])
  
  async function get_notifications_list(){
    let response = await loginApi.view_notifications();
    if (!response.was_exception){
      setNotifications_list(response.value)
    }
  }
  useEffect(() => {
    get_notifications_list();
  }, [])

    return (
      <View>
        <ImageBackground source={background} resizeMode="cover">
        <View style={{display: 'flex', flexDirection:'row', width: 550}}>
        <View style={{width:750}}>
        <Text style={{textAlign:'center', color:'#841584', backgroundColor:'white', opacity:0.8, width: 350}}><h1>   </h1></Text>
        <Button onPress={() => navigation.navigate('Users')} title="Users Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Admins')} title="Admins Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Advertisements')} title="Advertisements Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Questions')} title="Questions Window" title_color='black' color='#00000000'/>
        <Button onPress={() => navigation.navigate('Hazards')} title="Hazards Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Rides')} title="Rides Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Statistics')} title="Statistics Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Awards')} title="Awards Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('SystemSettings')} title="System settings Window" color='#00000000'/>
        {/* <Button onPress={() => navigation.navigate('VisualRoute')} title="Visual Window" color='#00000000'/> */}
        </View>
        {/* <View style={{display: 'flex', flexDirection:'column', width: 450}}>
        {showTextBox &&
                  <ScrollView style={styles.container}>
                                      <Text style={{textAlign:'center', color:'#841584', backgroundColor:'white', opacity:0.8}}><h1>Notifications</h1></Text>
                  <Table columns={columns} data={notifications_list} tableLayout="auto"/>
                  </ScrollView>
         
      }
      </View> */}
        {/* <View style={{display: 'flex', flexDirection:'column', width: 550}}>
        <Text style={{textAlign:'center', color:'#841584', backgroundColor:'white', opacity:0.8}}><h1>Notifications</h1></Text>
        <ScrollView style={styles.container}>
        <Table columns={columns} data={notifications_list} tableLayout="auto"/>
        </ScrollView>
        </View> */}


        </View>
        </ImageBackground>
      </View>
    );
  }


  const columns = [
    {
      title: " ",
      dataIndex: "_message",
      key: "_message",
      width: 200,
    },
  ];
  
  
  
  const styles = StyleSheet.create({
    container: {
      width:550,
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
      textAlign:'center',
      height: 40
    },
    item: {
      padding: 20,
      fontSize: 15,
      marginTop: 5,
    }
  });



