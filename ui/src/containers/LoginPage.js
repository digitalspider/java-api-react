import React, {Component} from 'react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Section from '../components/common/Section';
import { Input, Button, Grid } from '@material-ui/core';
import Api from '../utils/Api';

const styles = (theme) => ({
  section: {
    padding: '15px 0',
  },
});

class LoginPage extends Component {
  handleLogin() {
    let username = 'admin';
    let password = 'admin';
    let data = {
      username: username,
      password: password,
    }
    Api.post('/user/login', data);
  }

  render() {
    const {classes} = this.props;
    return (
      <Section className={classes.section}>
        <Typography variant="h4" gutterBottom>
        Login Page
        </Typography>
        <Grid>
          <Grid item>
            <Input inputType='text' placeholder='Username' value=''/>
          </Grid>
          <Grid item>
            <Input inputType='password' placeholder='Password' value=''/>
          </Grid>
        </Grid>
        <Button
            variant='raised'
            color="primary"
            onClick={this.handleLogin}
            style={{marginRight: '10px'}}
          >
            Login
          </Button>
          <Button
            variant='raised'
            onClick={this.handleForgot}
            color="secondary"
          >
            Forgot Password?
          </Button>
      </Section>
    );
  }
}

export default withStyles(styles)(LoginPage);
