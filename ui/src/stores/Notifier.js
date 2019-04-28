import {observable, action} from 'mobx';

/*
 * Store implemented to handle snack notifications
 */
class Notifier {
  @observable title = '';
  @observable content = [];
  @observable icon = '';
  @observable open = false;
  @observable type = '';

  // Handles close event on the notifier (snack) element
  @action
  handleClose(value) {
    this.open = false;
  }

  // Displays a notifier (snack) element with a given title and content
  @action
  display(title, content) {
    let messages = !Array.isArray(content) ? [content] : content;
    this.title = title;
    this.content = messages;
    this.open = true;
  }

  // Displays a success message
  @action
  displaySuccess(title, content) {
    this.type = 'success';
    this.display(title, content);
  }

  // Displays a success message
  @action
  displayWarning(title, content) {
    this.type = 'warning';
    this.display(title, content);
  }

  // Displays a success message
  @action
  displayError(title, content) {
    this.type = 'error';
    this.display(title, content);
  }
}

export default new Notifier();
