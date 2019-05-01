import {createMuiTheme} from '@material-ui/core/styles';
import themeCommon from './themeCommon';
import themeDefault from './themeDefault';
import _ from 'lodash';

let getTheme = (customTheme) => {
  let newTheme = customTheme ? customTheme : themeDefault;
  return createMuiTheme(_.merge({}, themeCommon, newTheme));
};

export default getTheme;
