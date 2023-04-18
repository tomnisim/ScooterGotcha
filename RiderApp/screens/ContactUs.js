// import React, { useState } from 'react';

// export default function ConatctUs({navigation}) {
//   const [name, setName] = useState('');
//   const [email, setEmail] = useState('');
//   const [message, setMessage] = useState('');

//   const handleNameChange = (event) => {
//     setName(event.target.value);
//   };

//   const handleEmailChange = (event) => {
//     setEmail(event.target.value);
//   };

//   const handleMessageChange = (event) => {
//     setMessage(event.target.value);
//   };

//   const handleSubmit = (event) => {
//     event.preventDefault();
//     // Submit logic goes here
//     console.log(`Name: ${name}\nEmail: ${email}\nMessage: ${message}`);
//   };

//   return (
//     <div>
//       <h1>Contact Us</h1>
//       <form onSubmit={handleSubmit}>
//         <label>
//           Name:
//           <input type="text" value={name} onChange={handleNameChange} />
//         </label>
//         <br />
//         <label>
//           Email:
//           <input type="email" value={email} onChange={handleEmailChange} />
//         </label>
//         <br />
//         <label>
//           Message:
//           <textarea value={message} onChange={handleMessageChange} />
//         </label>
//         <br />
//         <button type="submit">Submit</button>
//       </form>
//     </div>
//   );
// };

import React,{ useState } from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
// import {LoginApi } from '../API/LoginApi';
import { background } from '../API/Paths';


// const loginApi = new LoginApi();



export default function ConatctUs({navigation}) {
  // todo: change default
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleMessageChange = (event) => {
    setMessage(event.target.value);
  };

//   const login = async () => {
//     let response = await loginApi.login(user_email, user_password)
//     console.log(response)
//     if (response.was_exception || response.was_exception == undefined){
//         if (response.was_exception == undefined)
//             alert("no connection")
//         else
//             alert(response.message)
//     }
//     else
//     {
//         navigation.navigate('Home')
//     }
    

  const handleSubmit = (event) => {
    event.preventDefault();
    
    // Submit logic goes here
    console.log(`Name: ${name}\nEmail: ${email}\nMessage: ${message}`);
  };

    return (
        
        <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'center'}}>
            <Text style={{color:'white', backgroundColor:"#841584", width:350, opacity:0.8, textAlign:'center'}}><h3>Contact Us</h3></Text>
            <TextInput
                style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
                placeholder="Enter full name"
                onChangeText={newText => setName(newText)}
                />
            <TextInput
                style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:40, opacity:0.8}}
                placeholder="Enter Email"
                onChangeText={newText => setEmail(newText)}
                />
            <TextInput
                style={{color:"#841584",textAlign:'center',width:350, backgroundColor:'white',height:250, opacity:0.8}}
                placeholder="Enter a message"
                onChangeText={newText => setMessage(newText)}
                />
              <Button onPress={() => handleSubmit()}  title="Send" color="#841584"/>
            </View>
            </ImageBackground>
        </View>
      );
    }

    const styles = StyleSheet.create({
        image: {
            flex: 1,
            justifyContent: 'center',
          },
        container: {
          flex: 1,
          padding: 50,
        },
        item: {
          padding: 20,
          fontSize: 15,
          marginTop: 5,
        }
      });
      
      
      
      
              
