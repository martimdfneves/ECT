import React, {useState, useRef} from 'react';
import ReactMapGL, { Marker } from 'react-map-gl';
import useSwr from 'swr'
import "./App.css";

const fetcher = (...args) => fetch(...args).then(response => response.json());


export default function Maps() {
    const [viewport, setViewport] = useState({
        latitude: 40.063888,
        longitude: -8.16133948,
        width: window.innerWidth,
        height: window.innerHeight,
        zoom: 10
    });
    const mapRef = useRef();

    const url =
        "http://192.168.160.87:11003/fighters/gps";

    const {data, error} = useSwr(url, fetcher);
    const bombeirosGPS = data && !error ? data.slice(0,100) : [];

    return (

        <div>
            <ReactMapGL
                {...viewport}
                mapboxApiAccessToken={
                    "pk.eyJ1IjoicnViZW5tZW5pbm8iLCJhIjoiY2tweTB0Y2xvMDgzYjJvbzYyYWowMDZ1ciJ9.ytkMCweXVZCHVkBgbsfxHQ"
                }
                onViewportChange={(newView) => setViewport(newView)}
            >

                {bombeirosGPS.map(gps => (
                    <Marker
                        key={gps.id}
                        latitude={gps.gps_tag_lat}
                        longitude={gps.gps_tag_long}

                    >
                        <button className="crime-marker">
                            <img src="/fire.svg" alt="fire" />
                        </button>
                    </Marker>
                ))}
            </ReactMapGL>
            <p>ola</p>
        </div>

    );
}
