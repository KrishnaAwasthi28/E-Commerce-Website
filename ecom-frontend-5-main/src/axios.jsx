import axios from "axios";

const API = axios.create({
  baseURL: "https://e-commerce-website-1-xzpr.onrender.com/api",
});
delete API.defaults.headers.common["Authorization"];
export default API;
