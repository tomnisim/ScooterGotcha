import Navigation from '../navigation';
import { RootTabScreenProps } from '../types';
import { StyleSheet, View, TouchableOpacity, Text, Button } from 'react-native';


import React from 'react';

interface Ride {
  id: number;
  date: string;
  information: string;
  // add more properties as needed
}

interface Props {
  rides: Ride[];
}

function MyRides  ({ rides }: Props, { navigation }: RootTabScreenProps<'MyRides'>)  {
  const ridess=[1,2,3,4,5,5,6,7,8,8]
  return (
    <div>
      <h1>My Rides</h1>
      <table className="table">
        <thead>
          <tr>
            <th>Ride Date</th>
            <th>Ride Information</th>
          </tr>
        </thead>
        <tbody>
          {ridess.map((ride, index) => (
            <React.Fragment key={'ride.id'}>
              <tr className={index % 2 === 0 ? 'even' : 'odd'}>
                <td>{'ride.date'}</td>
                <td>{'ride.information'}</td>
              </tr>
            </React.Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
}






export default MyRides;











const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5fcff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  description: {
    fontSize: 18,
    textAlign: 'center',
    marginHorizontal: 20,
  },
  button: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
  },
  even :{
    backgroundColor : '#f5f5f5'
  }, 

  

});

