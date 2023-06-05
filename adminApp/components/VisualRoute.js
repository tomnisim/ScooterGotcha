import React, { useEffect } from 'react';
import { View, WebView } from 'react-native';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

import logoIcon from 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/main/adminApp/assets/logo.png';

const junctions = [
  { id: 0, name: 'Junction 0', lat: 31.265106, lng: 34.801402 },
  { id: 1, name: 'Junction 1', lat: 31.267134, lng: 34.800485 },
  { id: 2, name: 'Junction 2', lat: 31.267604, lng: 34.7975587 },
];

const myIcon = L.icon({
  iconUrl: logoIcon,
  iconSize: [30, 30],
  iconAnchor: [15, 15],
});

export default function VisualRoute() {
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
      const marker = L.marker([junction.lat, junction.lng], { icon: myIcon });
      marker.addTo(map);
      if (index === 0) {
        marker.bindPopup(`<b><div style='background-color:orange'>Origin</div></b>`);
        markers.push(marker);
      } else if (index === junctions.length - 1) {
        marker.bindPopup(`<b><div style='background-color:orange'>Destination</div></b>`);
      } else {
        marker.bindPopup(`<b>${junction.name}</b>`);
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

      markers.push(marker);
    });

    // Open the popups for all the markers
    // markers.forEach((marker) => marker.openPopup());
  }, []);

  return (
    <View style={{ flex: 1 }}>
      <WebView
        source={{ html: '<div id="map" style="height: 100%;"></div>' }}
        originWhitelist={['*']}
        javaScriptEnabled={true}
        domStorageEnabled={true}
        style={{ flex: 1 }}
      />
    </View>
  );
}
