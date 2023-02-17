import { createGlobalStyle } from 'styled-components';
import Roboto from '../assets/Roboto-Regular.ttf';

const Typography = createGlobalStyle`
@font-face {
    font-family: 'Roboto-Regular';
    src: url(${Roboto});
    font-style: normal;
  }

  *{
    font-family: 'Roboto-Regular' !important;
  }`;

export default Typography;