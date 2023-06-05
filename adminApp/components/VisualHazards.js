import React, { useEffect } from 'react';
import { View } from 'react-native';
import { WebView } from 'react-native-webview';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

import warningIcon from 'https://raw.githubusercontent.com/tomnisim/ScooterGotcha/main/adminApp/assets/logo.png';

const junctions = [
  { id: 0, name: 'Junction 0', lat: 31.265106, lng: 34.801402 },
  { id: 1, name: 'Junction 1', lat: 31.267134, lng: 34.800485 },
  { id: 2, name: 'Junction 2', lat: 31.267604, lng: 34.7975587 },
];

const myIcon = L.icon({
  iconUrl: warningIcon,
  iconSize: [30, 30],
  iconAnchor: [15, 15],
});

export default function VisualHazards() {
  useEffect(() => {
    // Calculate the center of the junctions
    const latitudes = junctions.map((junction) => junction.lat);
    const longitudes = junctions.map((junction) => junction.lng);
    const centerLat = (Math.min(...latitudes) + Math.max(...latitudes)) / 2;
    const centerLng = (Math.min(...longitudes) + Math.max(...longitudes)) / 2;

    // Initialize the Leaflet map
    const map = L.map('map').setView([centerLat, centerLng], 15);

    // Add the tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
    }).addTo(map);

    // Add the junction markers to the map
    junctions.forEach((junction, index) => {
      const marker = L.marker([junction.lat, junction.lng], { icon: myIcon });
      marker.addTo(map);
      marker.bindPopup(`<b><div style='background-color:orange'>${junction.name}</div></b>`);
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
