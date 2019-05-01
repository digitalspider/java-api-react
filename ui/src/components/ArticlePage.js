import React, {Component} from 'react';
// import {inject, observer} from 'mobx-react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Section from './common/Section';
import CircularProgress from '@material-ui/core/CircularProgress';

const styles = (theme) => ({
  section: {
    padding: '15px 0',
  },
});

// @inject('article')
// @observer
class ArticlePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fetchComplete: false,
    };
  }

  componentDidMount() {
    Promise.all([
      // this.props.articles.all(),
    ]).then(() => this.setState({fetchComplete: true}));
  }

  render() {
    const {classes} = this.props;
    return (
      <Section className={classes.section}>
        {!this.state.fetchComplete ?
          ( <CircularProgress /> ) :
          ( <div id="page">
            <Typography variant="h4" gutterBottom>
            Article Page
            </Typography>
          </div>
          )
        }
      </Section>
    );
  }
}

export default withStyles(styles)(ArticlePage);
