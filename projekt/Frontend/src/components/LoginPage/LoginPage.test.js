import React from 'react';
import Adapter from 'enzyme-adapter-react-16';
import {configure, mount} from 'enzyme';
import LoginPage from './LoginPage';
import { MemoryRouter } from "react-router-dom";
configure({adapter: new Adapter()})

describe('<LoginPage />', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(<MemoryRouter initialEntries={[ '/' ]} > <LoginPage /></MemoryRouter >);
  })

  it('should render', () => {
    expect(wrapper).toHaveLength(1);
  });

  it('should have form', () => {
    expect(wrapper.find('#loginPageForm')).toHaveLength(1);

  })
})

