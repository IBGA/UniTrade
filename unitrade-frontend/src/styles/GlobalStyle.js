import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
*{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }
  
  :root{
    --primary: #2EAF7D;
    --secondary: #00ADB5;
    --black: #000000;
    --white: #FFFFFF;
    --grey: #02353C;
    --pale: #C1F6ED;
    --green: #449342;
    --red: #b52a34;
  }
  ul, li{
    list-style: none;
  }
  a {
    text-decoration: none;
  }

`;
export default GlobalStyles;