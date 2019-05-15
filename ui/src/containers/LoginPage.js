import React, {Component} from 'react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Section from '../components/common/Section';
import { Input, Button, Grid } from '@material-ui/core';
import Api from '../utils/Api';
import Storage from '../utils/Storage';

const BASE_URL=process.env.REACT_APP_BASE_URL;

const styles = (theme) => ({
  section: {
    padding: '15px 0',
  },
});

class LoginPage extends Component {
  constructor(){
    super();
    this.state = {
      username : '',
      password : '',
    }
  }

  handleLogin = async () => {
    // let username = 'admin';
    // let password = 'admin';
    let data = {
      username: this.state.username,
      password: this.state.password,
    }
    let response = await Api.post(BASE_URL+'/user/login', data);
    console.log('resp='+JSON.stringify(response));
    if (response.status===200) {
      console.log('New token='+response.data.token);
      console.log('User='+response.data.username);
      Storage.setSession(response.data);
      window.push('/');
    } else if (response.status===401) {
      console.log('Invalid login!!!');
    } else {
      console.log(response);
    }
  }

  handleUsername = (event) => {
    this.setState({username : event.target.value})
  }

  handlePassword = (event) => {
    this.setState({password : event.target.value})
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
            <Input inputType='text' placeholder='Username' onChange={this.handleUsername}/>
          </Grid>
          <Grid item>
            <Input inputType='password' placeholder='Password' onChange={this.handlePassword}/>
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
