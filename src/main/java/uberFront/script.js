document.getElementById('request-ride').addEventListener('click', function() {
    const origin = document.getElementById('origin').value;
    const destination = document.getElementById('destination').value;

    if (origin && destination) {
        // Fetch driver details from the API
        fetch('https://pqlm69oyni.execute-api.us-east-1.amazonaws.com/Uber2/')
            .then(response => response.json())
            .then(data => {
                // Update the labels with the driver details
                document.getElementById('driver-name').textContent = `Conductor: ${data.driverName}`;
                document.getElementById('driver-plate').textContent = `Placa: ${data.Plate}`;
            })
            .catch(error => {
                console.error('Error fetching driver details:', error);
            });
    } else {
        alert('Por favor, completa ambos campos: Origen y Destino.');
    }
});

document.getElementById('start-ride').addEventListener('click', function() {
    const origin = document.getElementById('origin').value;
    const destination = document.getElementById('destination').value;

    if (origin && destination) {
        // Fetch route data from the API
        fetch(`https://pqlm69oyni.execute-api.us-east-1.amazonaws.com/Uber2/getRoute?route=${origin},${destination}`)
            .then(response => response.json())
            .then(data => {
                let locations = Object.values(data);
                let index = 0;

                function updateMap() {
                    if (index < locations.length) {
                        const [lat, lng] = locations[index];
                        const mapElement = document.getElementById('map');
                        mapElement.innerHTML = `<iframe width="100%" height="100%" src="https://maps.google.com/maps?q=${lat},${lng}&t=&z=13&ie=UTF8&iwloc=&output=embed" frameborder="0" allowfullscreen></iframe>`;
                        index++;
                    } else {
                        clearInterval(interval);
                    }
                }

                // Update the map every 3 seconds
                const interval = setInterval(updateMap, 3000);
                updateMap(); // Call it once immediately
            })
            .catch(error => {
                console.error('Error fetching route data:', error);
            });
    } else {
        alert('Por favor, completa ambos campos: Origen y Destino.');
    }
});
