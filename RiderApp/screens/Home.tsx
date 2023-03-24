

// import { StyleSheet, View, TouchableOpacity, Text, Button } from 'react-native';
// // const CenterButtonScreen = () => {
// //     return (
// //       <View style={styles.container}>
// //         <TouchableOpacity style={styles.button}>
// //           <Text style={styles.buttonText}>Click Me</Text>
// //         </TouchableOpacity>
// //       </View>
// //     );
// //   };
// function HomeScreen  ({ navigation }: RootTabScreenProps<'Home'>)  {
//     console.log(navigation)
//   return (
//     <View style={styles.container}>
//        <Button title='My Profile' onPress={()=>{navigation.navigate('Profile')}} ></Button> 
//        <Button title='Register' onPress={()=>{navigation.navigate('Register')}} ></Button> 
//        <Button title='Start Ride'></Button> 
//        <Button title='Announcments'></Button> 
//        <Button title='Contact Us'></Button> 
//       <Text style={styles.title}>Gotcha</Text>
//       <Text style={styles.description}>This is the home page of my app</Text>
//     </View>
//   );
// };

// const styles = StyleSheet.create({
//   container: {
//     flex: 1,
//     justifyContent: 'center',
//     alignItems: 'center',
//     backgroundColor: '#f5fcff',
//   },
//   title: {
//     fontSize: 24,
//     fontWeight: 'bold',
//     marginBottom: 10,
//   },
//   description: {
//     fontSize: 18,
//     textAlign: 'center',
//     marginHorizontal: 20,
//   },
//   button: {
//     backgroundColor: '#3498db',
//     padding: 10,
//     borderRadius: 5,
//   },
//   buttonText: {
//     color: '#fff',
//     fontSize: 18,
//   },
// });

// export default HomeScreen;



import Navigation from '../navigation';
import { RootTabScreenProps } from '../types';

import React, {useState} from 'react';
import {
  FlatList,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

type ItemData = {
  id: string;
  title: string;
  nev : () => void;
};



type ItemProps = {
  item: ItemData;
  onPress: () => void;
  backgroundColor: string;
  textColor: string;
};

const Item = ({item, onPress, backgroundColor, textColor}: ItemProps) => (
  <TouchableOpacity onPress={onPress} style={[styles.item, {backgroundColor}]}>
    <Text style={[styles.title, {color: textColor}]}>{item.title}</Text>
  </TouchableOpacity>
);

const HomeScreen = ({ navigation }: RootTabScreenProps<'Home'>) => {
  const nev = navigation
  const DATA: ItemData[] = [
    {
      id: 'MyProfile',
      title: 'My Profile',
      nev : () => nev.navigate('Profile'),
    },
    {
      id: 'StartRide',
      title: 'Start Ride',
      nev : () => nev.navigate('StartRide'),
    },
    {
      id: '58694a0f-3da1-471f-bd96-145571e29d72',
      title: 'Third Item',
      nev : () => nev.navigate('Profile'),
    },
  ];
  
  const [selectedId, setSelectedId] = useState<string>();

  
  const renderItem = ({item}: {item: ItemData}) => {
    const backgroundColor = item.id === selectedId ? '#6e3b6e' : '#f9c2ff';
    const color = item.id === selectedId ? 'white' : 'black';

    return (
     
      

        <Item
          item={item}
          onPress={() => {setSelectedId(item.id); item.nev()}}
          backgroundColor={backgroundColor}
          textColor={color}
        />
       
    );
  };

  return (
    <SafeAreaView style={styles.container}>
       <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={styles.title}>Hello User Name</Text>
        <FlatList
        data={DATA}
        renderItem={renderItem}
        keyExtractor={item => item.id}
        extraData={selectedId}
        numColumns={1}
      />
      </View>
      
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: StatusBar.currentHeight || 0,
  },
  item: {
    padding: 20,
    marginVertical: 8,
    marginHorizontal: 16,
  },
  title: {
    fontSize: 32,
  },
});

export default HomeScreen;