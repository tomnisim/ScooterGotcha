import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import L, { icon } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { destIcon, originIcon, hazardIcon, liveLocationIcon} from '../API/AppConstans';


let junctions = [
    { id: 0, name: 'Junction 0', lat: 31.265106, lng: 34.801402 },
    { id: 1, name: 'Junction 1', lat: 31.267134, lng: 34.800485 },
    { id: 2, name: 'Junction 2', lat: 31.267604, lng: 34.7975587 },]

let distance = "0"
let duration = "0"

let hazards = []

export const setRoute = ((route) => {
  let value = route[0]
  console.log(value)
  distance = value.distance
  duration = value.duration
  let junctionsNumber = value.number_of_junctions
  let juns = value.junctions
  let id = 0
  const lst = juns.map((jun) => {
    id = id + 1;
    return {id: id, name:'Junction '+id, lng: jun.longitude,lat: jun.latitude}

  })
  let hazards = value.hazardsInRoute;
  let hazard_id = 0
  const hazards_list = hazards.map((hazard) => {
    hazard_id = hazard_id +1;
    return {hazard_id: hazard_id, lng: hazard.location.longitude, lat: hazard.location.latitude, type: hazard.type}
  })


  console.log("distance:"+distance)
  console.log("duration:"+duration)
  console.log("numbers:"+junctionsNumber)


  set_junctions(lst)
  set_hazards(hazards_list)

})
export const set_junctions = async (val) => {
    junctions = val
  }
  export const set_hazards = async (val) => {
    hazards = val
  }

export default function VisualRouteScreen({}) {

  const [currentLocation, setCurrentLocation] = useState(null);
  const [locationRetrieved, setLocationRetrieved] = useState(false);
  
  
  // navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
  
  //   function successCallback(position) {
  //   const lat = position.coords.latitude;
  //   const lng = position.coords.longitude;
  //   console.log(lat)
  //   console.log(lng)
  //   // const destination = "123 Main St, Anytown USA";
  //   // const apiKey = "your_api_key_here";
  //   // const url = `https://maps.googleapis.com/maps/api/directions/json?origin=${lat},${lng}&destination=${destination}&key=${apiKey}`;
    
  //   // fetch(url)
  //   //   .then(response => response.json())
  //   //   .then(data => {
  //   //     const steps = data.routes[0].legs[0].steps;
  //   //     steps.forEach(step => {
  //   //       const instructions = step.html_instructions;
  //   //       // display instructions to user
  //   //     });
  //   //   })
  //   //   .catch(error => {
  //   //     console.error(error);
  //   //   });
  // }
  
  // function errorCallback(error) {
  //   console.error(error);
  // }

    useEffect(() => {
        // Calculate the center of the junctions
        const latitudes = junctions.map((junction) => junction.lat);
        const longitudes = junctions.map((junction) => junction.lng);
        const centerLat = (Math.min(...latitudes) + Math.max(...latitudes)) / 2;
        const centerLng = (Math.min(...longitudes) + Math.max(...longitudes)) / 2;
    
        // Initialize the Leaflet map
        const map = L.map('map').setView([centerLat, centerLng], 13);
    
        // Add the tile layer
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
        }).addTo(map);
    
        // Create an array to hold the markers
        const markers = [];
    
        hazards.forEach((hazard) => {
          const marker = L.marker([hazard.lat, hazard.lng], {icon: hazardIcon});
          marker.addTo(map);
          marker.bindPopup(`<b>${hazard.type}</b>`);
          markers.push(marker);
          },);




        // Add the junction markers to the map
        junctions.forEach((junction, index) => {
          if (index == 0){
            const marker = L.marker([junction.lat, junction.lng], {icon: originIcon});
            marker.addTo(map);
            marker.bindPopup(`<b>Origin<b>`);
            markers.push(marker);
          }
          else{
            if (index == junctions.length-1){
                const marker = L.marker([junction.lat, junction.lng], {icon: destIcon});
                marker.addTo(map);
                marker.bindPopup(`<b>Destination<b>`);
                markers.push(marker);

            }
            else
            {
              // const marker = L.marker([junction.lat, junction.lng], {icon: myIcon});
              // marker.addTo(map);
              // marker.bindPopup(`<b>${junction.name}</b>`);

  
            }
          }


          // Draw the polyline between the current junction and the previous junction
          if (index > 0) {
            const previousJunction = junctions[index - 1];
            const polyline = L.polyline(
              [
                [previousJunction.lat, previousJunction.lng],
                [junction.lat, junction.lng],
              ],
              { color: 'red' }
            ).addTo(map);
          }
            });
    
        
            navigator.geolocation.getCurrentPosition(
              (position) => {
                console.log(position);
                const { latitude, longitude } = position.coords;
                const marker = L.marker([latitude, longitude], {icon: liveLocationIcon});
                marker.addTo(map);
                marker.bindPopup(`<b>Current Location<b>`);
                markers.push(marker);
                
                setCurrentLocation([latitude, longitude]);
                setLocationRetrieved(true);
    
              },
              (error) => {
                console.error(error);
                setLocationRetrieved(true);
              }
            );
        // Open the popups for all the markers
        //markers.forEach((marker) => marker.openPopup());
      }, [junctions, hazards]);
    
      return (
        <View>
        <div id="map" style={{ height: '500px' }}>
        </div>
        <h3>Distance: {distance} Meters</h3>
          <h3>Duration: {duration} Minutes</h3>
        </View>
      );
    };
    
