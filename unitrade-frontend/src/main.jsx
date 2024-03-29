import React from 'react'
import ReactDOM from 'react-dom/client'

import 'bootstrap/dist/css/bootstrap.min.css';

import App from './app'
import GlobalStyles from './styles/GlobalStyle';
import Typography from './styles/Typography';
import { AuthProvider } from './components/AuthProvider';

ReactDOM.createRoot(document.getElementById('root')).render(
  <>
    <GlobalStyles />
    <Typography />
    <AuthProvider>
      <App />
    </AuthProvider>
  </>     
)
