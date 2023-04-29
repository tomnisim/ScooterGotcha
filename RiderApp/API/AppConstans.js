import L, { icon } from 'leaflet';
export const path = "http://localhost:5050/";
export const CONNECTION_ERROR = "The system is not available right now, come back later";
export const CATCH = "CATCH";
export const background = {uri: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/main/adminApp/assets/background.png'};
export const hazardIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/119-my-rides-screen/RiderApp/assets/warning.png',
    iconSize: [30, 30],
    iconAnchor: [15, 15]
  });


export const originIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/119-my-rides-screen/RiderApp/assets/origin.png',
    iconSize: [30, 30],
    iconAnchor: [15, 15]
  });

export  const destIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/119-my-rides-screen/RiderApp/assets/origin.png',
    iconSize: [30, 30],
    iconAnchor: [15, 15]
  });

  export  const liveLocationIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/119-my-rides-screen/RiderApp/assets/liveLocation.png',
    iconSize: [30, 30],
    iconAnchor: [15, 15]
  });