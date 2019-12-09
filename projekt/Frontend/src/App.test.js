import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { mount } from 'enzyme';
import { MemoryRouter } from 'react-router';
import LoginPage from './components/LoginPage/LoginPage';
import Chat from './components/Chat/Chat';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App />, div);
  ReactDOM.unmountComponentAtNode(div);
});

test('invalid path should redirect to 404', () => {
  const wrapper = mount(
    <MemoryRouter initialEntries={[ '/' ]}>
      <App/>
    </MemoryRouter>
  );
  expect(wrapper.find(LoginPage)).toHaveLength(1);
  expect(wrapper.find(Chat)).toHaveLength(0);
});