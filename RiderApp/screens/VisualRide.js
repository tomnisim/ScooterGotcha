import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import L, { icon } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { destIcon, originIcon } from '../API/AppConstans';

let junctions = [
    { id: 0, name: 'Junction 0', lat: 31.265106, lng: 34.801402 },
    { id: 1, name: 'Junction 1', lat: 31.267134, lng: 34.800485 },
    { id: 2, name: 'Junction 2', lat: 31.267604, lng: 34.7975587 },]

let distance = "0"
let duration = "0"

export const setRide = ((ride) => {
  console.log(ride)
  let value = ride
  distance = value.distance
  duration = value.duration
  value.junctions.push(value.destination);
  value.junctions.push(value.origin);
  let junctionsNumber = value.junctions.length;
  let juns = value.junctions
  let id = 0
  const lst = juns.map((jun) => {
    id = id + 1;
    return {id: id, name:'Junction '+id, lng: jun.longitude,lat: jun.latitude}

  })
  console.log("distance:"+distance)
  console.log("duration:"+duration)
  console.log("numbers:"+junctionsNumber)


  set_junctions(lst)

})
export const set_junctions = async (val) => {
    junctions = val
  }



export default function VisualRideScreen({}) {

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
    
        // Open the popups for all the markers
        //markers.forEach((marker) => marker.openPopup());
        

        
        


      }, [junctions]);
    
      return (
        <View>
        <div id="map" style={{ height: '500px' }}>
        </div>
        <h3>Distance: {distance} [Meters]</h3>
          <h3>Duration: {duration} [Hours]</h3>
        </View>
      );
    };
    
