import React from 'react';
import ReactDOM from 'react-dom';
import MainRouter from './routers/MainRouter';
import './index.css';
// import stores from './stores';
// import {MuiThemeProvider} from '@material-ui/core/styles';
// import {Provider} from 'mobx-react';
// import Notifier from './components/Notifier';
// import theme from './utils/theme';
import {BrowserRouter} from 'react-router-dom/cjs/react-router-dom';

ReactDOM.render(
  // <MuiThemeProvider theme={theme}>
  //   <Provider {...stores}>
      <React.Fragment>
        <BrowserRouter>
          <MainRouter />
        </BrowserRouter>
        {/* <Notifier /> */}
      </React.Fragment>,
  //   </Provider>
  // </MuiThemeProvider>,
  document.getElementById('root')
);
