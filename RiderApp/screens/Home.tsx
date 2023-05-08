import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ScrollView ,View, Text, Button, ImageBackground, StyleSheet} from 'react-native';
import Table from 'rc-table';
import { background } from '../API/AppConstans';
import { UserApi } from '../API/UserApi';

const userApi = new UserApi();


export default function HomeScreen({navigation}) {
  const [advertisments_list, setAdvertisments_list] = useState([])
  const [notifications_list, setNotifications_list] = useState([])
  
  let images = []


  // async function get_notifications_list(){
  //   let response = await userApi.view_notifications();
  //   if (!response.was_exception){
  //     setNotifications_list(response.value)
  //   }
  // }

  async function get_advertisments_list(){
    let response = await userApi.view_advertisements();
    if (!response.was_exception){
      setAdvertisments_list(response.value)
      let temp = response.value
      images = temp.map((adv) => {
        return {url: adv.url, image:adv.photo, id: adv.id}
      })
      setAdvertisments_list(images)
    }
  }

  async function add_adv_click(adv_id){
    let response = await userApi.add_adv_click(adv_id)
  }

  useEffect(() => {
    //get_notifications_list();
    get_advertisments_list();
  }, [])

    return (
      <View>
        <ImageBackground source={background} resizeMode="cover">
        <View style={{display: 'flex', flexDirection:'row', width: 550}}>
        <View style={{width:750}}>
          <Text><h1>   </h1></Text>
        <Button onPress={() => navigation.navigate('Profile')} title="Profile Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('MyRides')} title="My Rides Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('Start Ride')} title="Start Ride Window" color='#00000000'/>
        <Button onPress={() => navigation.navigate('ContactUs')} title="Contact Us Window" color='#00000000'/>

        </View>

        <View style={{display: 'flex', flexDirection:'column', width: 250}}>
        <ScrollView style={styles.container}>
        <ul>
            {advertisments_list.map((item, index) => (
            <li key={index}>
              <a href={item.url}>
                <img src={item.image} onClick={() => add_adv_click(item.id)} alt={`Image ${index}`} />
              </a>
            </li>
          ))}
        </ul>
      </ScrollView>
        </View>

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

  const advs_columns = [
    {
      title: " ",
      dataIndex: "owner",
      key: "owner",
      width: 20,
    },
    {
      title: " ",
      dataIndex: "photo",
      key: "photo",
      width: 20,
    },
    {
      title: " ",
      dataIndex: "url",
      key: "url",
      width: 20,
    },
  ];
  
  
  
  
  const styles = StyleSheet.create({
    container: {
      width:550,
      height:350,
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