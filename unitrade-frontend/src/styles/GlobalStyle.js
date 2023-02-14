import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
*{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }
  
  :root{
    --primary: #00C2CB;
    --black: #000000;
    --white: #FFFFFF;
    --grey: #727072;
    --red: #E63946;
  }
  ul, li{
    list-style: none;
  }
  a {
    text-decoration: none;
  }
`;
export default GlobalStyles;