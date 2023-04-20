import AsyncStorage from '@react-native-async-storage/async-storage';


export class Utils{

    storeData = async (key, value) => {
        try {
            const jsonValue = JSON.stringify(value) 
            await AsyncStorage.setItem(key, jsonValue)
        } catch (e) {
            console.log("Error storing data:", e);
        }
    }
    getData = async (key) => {
        try {
          const jsonValue = await AsyncStorage.getItem(key);
          return jsonValue != null ? JSON.parse(jsonValue) : null;
        } catch(e) {
          console.log("Error retrieving data:", e);
        }
    }
    removeItem = async (key) => {
      try {
        await AsyncStorage.removeItem(key);
        console.log(`Item with key ${key} removed from AsyncStorage`);
      } catch (error) {
        console.log(`Error removing item from AsyncStorage: ${error}`);
      }
    }
    date_to_string = (date)=>{
        const year = date.getFullYear(); // get the year (e.g. 2023)
        const month = date.getMonth() + 1; // get the month (0-11) and add 1 to convert it to 1-12
        const day = date.getDate(); // get the day of the month (1-31)
    
        // format the date as a string in the format "YYYY-MM-DD"
        const dateString = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
        return dateString
      }
}