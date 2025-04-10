// MARK: PLAYFAIR DISPLAY

// TTF
import pf from './fonts/playfair_display/ttf/playfair-display.ttf';
import pfItalic from './fonts/playfair_display/ttf/playfair-display.ttf'

// WOFF
import pfWoff from './fonts/playfair_display/woff/playfair-display.woff';
import pfWoffItalic from './fonts/playfair_display/woff/playfair-display-italic.woff';

// WOFF2
import pfWoff2 from './fonts/playfair_display/woff2/playfair-display.woff2';
import pfWoff2Italic from './fonts/playfair_display/woff2/playfair-display-italic.woff2';

// MARK: POPPINS

// TTF
import pLight from './fonts/poppins/ttf/poppins-light.ttf';
import pLightItalic from './fonts/poppins/ttf/poppins-light-italic.ttf';
import pRegular from './fonts/poppins/ttf/poppins-regular.ttf';
import pRegularItalic from './fonts/poppins/ttf/poppins-regular-italic.ttf';
import pSBold from './fonts/poppins/ttf/poppins-semibold.ttf';
import pSBoldItalic from './fonts/poppins/ttf/poppins-semibold-italic.ttf'; 
import pMedium from './fonts/poppins/ttf/poppins-medium.ttf';
import pMediumItalic from './fonts/poppins/ttf/poppins-medium-italic.ttf';
import pBold from './fonts/poppins/ttf/poppins-bold-italic.ttf';
import pBoldItalic from './fonts/poppins/ttf/poppins-bold-italic.ttf';

// WOFF
import pLightWoff from './fonts/poppins/woff/poppins-light.woff';
import pLightItalicWoff from './fonts/poppins/woff/poppins-light-italic.woff';
import pRegularWoff from './fonts/poppins/woff/poppins-regular.woff';
import pRegularItalicWoff from './fonts/poppins/woff/poppins-regular-italic.woff';
import pSBoldWoff from './fonts/poppins/woff/poppins-semibold.woff';
import pSBoldItalicWoff from './fonts/poppins/woff/poppins-semibold-italic.woff'; 
import pMediumWoff from './fonts/poppins/woff/poppins-medium.woff';
import pMediumItalicWoff from './fonts/poppins/woff/poppins-medium-italic.woff';
import pBoldWoff from './fonts/poppins/woff/poppins-bold-italic.woff';
import pBoldItalicWoff from './fonts/poppins/woff/poppins-bold-italic.woff';

// WOFF2
import pLightWoff2 from './fonts/poppins/woff2/poppins-light.woff2';
import pLightItalicWoff2 from './fonts/poppins/woff2/poppins-light-italic.woff2';
import pRegularWoff2 from './fonts/poppins/woff2/poppins-regular.woff2';
import pRegularItalicWoff2 from './fonts/poppins/woff2/poppins-regular-italic.woff2';
import pSBoldWoff2 from './fonts/poppins/woff2/poppins-semibold.woff2';
import pSBoldItalicWoff2 from './fonts/poppins/woff2/poppins-semibold-italic.woff2'; 
import pMediumWoff2 from './fonts/poppins/woff2/poppins-medium.woff2';
import pMediumItalicWoff2 from './fonts/poppins/woff2/poppins-medium-italic.woff2';
import pBoldWoff2 from './fonts/poppins/woff2/poppins-bold-italic.woff2';
import pBoldItalicWoff2 from './fonts/poppins/woff2/poppins-bold-italic.woff2';

import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      main: '#F0932B',
      contrastText: '#FFF'
    },
    secondary: {
      main: '#C44536',
      contrastText: "#FFF"
    },
    text: {
      primary: '#2D3436',
      secondary: '#5E5E5E',
    },
    background: {
      default: '#FAF4EF',
      paper: '#FFFFFF',
    },
  },
  typography: {
    fontFamily: 'Poppins, Arial, sans-serif',
    h1: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    },
    h2: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    },
    h3: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    },
    h4: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    },
    h5: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    },
    h6: {
      fontFamily: "'Playfair Display', 'Bodoni MT',serif"
    }
  },
  components:{
    MuiCssBaseline: {
      styleOverrides: `
      @font-face {
        font-family: 'Playfair Display';
        src: url(${pfWoff2}) format('woff2'),
            url(${pfWoff}) format('woff'),
            url(${pf}) format('truetype');
        font-weight: 400 900;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Playfair Display';
        src: url(${pfWoff2Italic}) format('woff2'),
            url(${pfWoffItalic}) format('woff'),
            url(${pfItalic}) format('truetype');
        font-weight: 400 900;
        font-style: italic;
        font-display: swap;
      }

      @font-face {
        font-family: 'Poppins';
        src: url(${pLightWoff2}) format('woff2'),
            url(${pLightWoff}) format('woff'),
            url(${pLight}) format('truetype');
        font-weight: 300;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Poppins';
        src: url(${pLightItalicWoff2}) format('woff2'),
            url(${pLightItalicWoff}) format('woff'),
            url(${pLightItalic}) format('truetype');
        font-weight: 300;
        font-style: italic;
        font-display: swap;
      }

      @font-face {
        font-family: 'Poppins';
        src: url(${pRegularWoff2}) format('woff2'),
            url(${pRegularWoff}) format('woff'),
            url(${pRegular}) format('truetype');
        font-weight: 400;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Poppins';
        src: url(${pRegularItalicWoff2}) format('woff2'),
            url(${pRegularItalicWoff}) format('woff'),
            url(${pRegularItalic}) format('truetype');
        font-weight: 400;
        font-style: italic;
        font-display: swap;
      }

      @font-face {
        font-family: 'Poppins';
        src: url(${pMediumWoff2}) format('woff2'),
            url(${pMediumWoff}) format('woff'),
            url(${pMedium}) format('truetype');
        font-weight: 500;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Poppins';
        src: url(${pMediumItalicWoff2}) format('woff2'),
            url(${pMediumItalicWoff}) format('woff'),
            url(${pMediumItalic}) format('truetype');
        font-weight: 500;
        font-style: italic;
        font-display: swap;
      }

      @font-face {
        font-family: 'Poppins';
        src: url(${pSBoldWoff2}) format('woff2'),
            url(${pSBoldWoff}) format('woff'),
            url(${pSBold}) format('truetype');
        font-weight: 600;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Poppins';
        src: url(${pSBoldItalicWoff2}) format('woff2'),
            url(${pSBoldItalicWoff}) format('woff'),
            url(${pSBoldItalic}) format('truetype');
        font-weight: 600;
        font-style: italic;
        font-display: swap;
      }

      @font-face {
        font-family: 'Poppins';
        src: url(${pBoldWoff2}) format('woff2'),
            url(${pBoldWoff}) format('woff'),
            url(${pBold}) format('truetype');
        font-weight: 700;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Poppins';
        src: url(${pBoldItalicWoff2}) format('woff2'),
            url(${pBoldItalicWoff}) format('woff'),
            url(${pBoldItalic}) format('truetype');
        font-weight: 700;
        font-style: italic;
        font-display: swap;
      }
    }
  }`
}}});