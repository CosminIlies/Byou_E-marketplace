import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";

export class Resp {
  constructor(status, text) {
    this.status = status;
    this.text = text;
  }
}

export async function login(username, password) {
  const cookies = new Cookies();

  const response = await fetch("http://localhost:8080/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  });

  if (response.status !== 200) {
    return new Resp(response.status, "Invalid username or password");
  }

  const data = await response.json();

  const decoded = jwtDecode(data.jwt);

  cookies.set("jwt-authorization", data.jwt, {
    expires: new Date(decoded.exp * 1000),
  });
  window.location.href = "/";

  return new Resp(response.status, "Success");
}

export async function register(email, username, password, r_password, phone) {
  const formData = new FormData();
  formData.append("email", email);
  formData.append("username", username);
  formData.append("password", password);
  formData.append("r_password", r_password);
  formData.append("phone", phone);

  const response = await fetch("http://localhost:8080/register", {
    method: "POST",
    body: formData,
  });

  if (response.status !== 201)
    return new Resp(response.status, await response.text());
  else login(username, password);
}

export async function me() {
  const cookies = new Cookies();
  const response = await fetch("http://localhost:8080/me", {
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + cookies.get("jwt-authorization"),
    },
  });

  if (response.status !== 200) {
    return new Resp(response.status, "Invalid token");
  }

  return new Resp(response.status, await response.json());
}
