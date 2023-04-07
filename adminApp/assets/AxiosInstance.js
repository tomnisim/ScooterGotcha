import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const instance = axios.create({withCredentials:true});

// instance.interceptors.request.use(
//   async (config) => {
//     const jsessionId = await AsyncStorage.getItem('JSESSIONID'); 
//     if (jsessionId) {
//         console.log("In Interceptor: Session ID: "+ jsessionId);
//         config.headers.Cookie = `JSESSIONID=${jsessionId}`;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

export default instance;