import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Section from '../components/common/Section';
import CircularProgress from '@material-ui/core/CircularProgress';
import DataTable from '../components/DataTable';
import {Link} from '@material-ui/core';
import {Link as RouterLink} from 'react-router-dom';

const styles = (theme) => ({
  section: {
    padding: '15px 0',
  },
});

class ArticleList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      articles: {},
      fetchComplete: false,
    };
  }

  componentDidMount() {
    console.log(this.props.articles);
    Promise.all([
      // this.props.articles.getArticles(),
      // this.props.users.all(),
    ]).then((articles) => {
      this.setState({articles: articles});
      this.setState({fetchComplete: true});
    });
  }

  render() {
    const {classes} = this.props;
    const {articles} = this.state;
    return (
      <Section className={classes.section}>
        {!this.state.fetchComplete ?
          ( <CircularProgress /> ) :
          ( <div id="page">
            <Typography variant="h4" gutterBottom>
            Article List Page
            | <Link component={RouterLink} to='/user'>User Page</Link>
            | <Link component={RouterLink} to='/article'>Article Page</Link>
            </Typography>
            <DataTable title="Articles" paginateAlways={true} data={articles}
              headers={['Article Name', 'Status', 'Creator', 'Created Date']}/>
          </div>
          )
        }
      </Section>
    );
  }
}

export default inject('articles')(observer(withStyles(styles)(ArticleList)));