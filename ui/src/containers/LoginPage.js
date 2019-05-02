import React, {Component} from 'react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Section from '../components/common/Section';

const styles = (theme) => ({
  section: {
    padding: '15px 0',
  },
});

class LoginPage extends Component {
  render() {
    const {classes} = this.props;
    return (
      <Section className={classes.section}>
        <Typography variant="h4" gutterBottom>
        Login Page
        </Typography>
      </Section>
    );
  }
}

export default withStyles(styles)(LoginPage);
