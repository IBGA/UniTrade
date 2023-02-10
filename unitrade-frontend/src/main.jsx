import React from 'react'
import ReactDOM from 'react-dom/client'

import 'bootstrap/dist/css/bootstrap.min.css';
import.meta.hot

import { BrowserRouter } from "react-router-dom";

import Router from './router'

ReactDOM.createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <Router />
  </BrowserRouter>
)
