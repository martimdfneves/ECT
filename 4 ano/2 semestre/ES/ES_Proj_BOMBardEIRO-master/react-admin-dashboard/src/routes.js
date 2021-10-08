/*!

=========================================================
* Light Bootstrap Dashboard React - v2.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/light-bootstrap-dashboard-react
* Copyright 2020 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/light-bootstrap-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/

import Maps from "views/Maps.js";
import ListFireComponent from "views/ListFireComponent.js"



const dashboardRoutes = [

  {
    path: "/info",
    name: "Info",
    icon: "nc-icon nc-chart-pie-35",
    component: ListFireComponent,
    layout: "/admin",
  },

  {
    path: "/maps",
    name: "Maps",
    icon: "nc-icon nc-pin-3",
    component: Maps,
    layout: "/admin",
  },


];

export default dashboardRoutes;
