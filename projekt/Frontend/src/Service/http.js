import Axios from "axios";
import {BACKEND_URL} from "./settings";

export const MainService = {
  login: (credentials) => Axios.post(`${BACKEND_URL}/api/login`, credentials),
  register: (credentials) => Axios.post(`${BACKEND_URL}/api/register`, credentials),
  history: () => Axios.get(`${BACKEND_URL}/api/history`),
}