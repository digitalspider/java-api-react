import {createMuiTheme} from '@material-ui/core/styles';

const theme = createMuiTheme({
  palette: {
    primary: {
      light: '#abcdef',
      main: '#123456',
      dark: '#001122',
      100: '#223344',
    },
    secondary: {
      light: '#00000',
      main: '#FFF',
      dark: '#6c6c6c',
      contrastText: '#515151',
    },
  },
  typography: {
    fontFamily: 'Ariel',
  },
  overrides: {
    MuiCircularProgress: {
      root: {
        display: 'block',
        margin: '20px auto',
      },
    },
  },
});

export default theme;
