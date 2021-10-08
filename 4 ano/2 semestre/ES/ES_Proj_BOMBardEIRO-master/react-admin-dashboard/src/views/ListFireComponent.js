import React, { Component } from 'react';
import StudentServices from './StudentServices';

class ListFireComponent extends Component {
    constructor() {
        super();
        this.state = {
            students: []
        }
    }

    componentDidMount() {
        StudentServices.getFireEnv().then((res) => {
            this.setState({ students: res.data });
        }
        );
    }

    render() {
        return (
            <div>
                <h2 className="text-center"> FireFighter ENV List </h2>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <tbody>
                            <tr>
                                <th>Date</th>
                                <th>Name</th>
                                <th>Type</th>
                                <th>CO</th>
                                <th>Temp</th>
                                <th>Hgt</th>
                                <th>NO2</th>
                                <th>Hum</th>
                                <th>Lum</th>
                                <th>Battery</th>
                             
                              
                            </tr>
                        </tbody>
                        <tbody>
                            {
                                this.state.students.map(
                                    students =>
                                        <tr key={students.id}>
                                            <td> {students.date} </td>
                                            <td> {students.name} </td>
                                            <td> {students.type} </td>
                                            <td> {students.co} </td>
                                            <td> {students.temp} </td>
                                            <td> {students.hgt} </td>
                                            <td> {students.no2} </td>
                                            <td> {students.hum} </td>
                                            <td> {students.lum} </td>
                                            <td> {students.battery} </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>

                </div>
            </div>
        );
    }
}

export default ListFireComponent;