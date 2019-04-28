import CssBaseline from '@material-ui/core/CssBaseline';
import {withStyles} from '@material-ui/core/styles';
import React from 'react';
import NavBar from '../components/NavBar';
import AppRouter from './../routers/AppRouter';
import Footer from './Footer';
import Section from '../components/common/Section';
import Favicon from 'react-favicon';
import getAgencyTheme from '../theme/index';

const styles = {
  main: {
    marginTop: 100,
  },
};

class Main extends React.Component {
  render() {
    let url = `${window.location.origin}${theme.favicon}`;
    const {classes} = this.props;
    return (
      <React.Fragment>
        <CssBaseline />
        <NavBar />
        <Section className={classes.main}>
          <AppRouter />
        </Section>
        <Footer />
        <Favicon url={url}></Favicon>
      </React.Fragment>
    );
  }
}

export default withStyles(styles)(Main);
