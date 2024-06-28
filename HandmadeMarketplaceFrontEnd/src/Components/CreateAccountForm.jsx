import { useState } from "react";
import BgPattern from "../assets/pattern.svg";
import { register } from "../Systems/LoginSystem";

export default function CreateAccountForm() {
  const [email, setEmail] = useState("");
  const [username, setUsernae] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const [r_password, setRpassword] = useState("");
  const [error, setError] = useState("");

  const submit_form = async () => {
    const resp = register(email, username, password, r_password, phone);

    if (resp.status !== 201) setError(resp.text);
  };

  return (
    <div className="flex flex-col relative m-auto h-screen w-full snap-start">
      <img src={BgPattern} className="absolute -z-10 w-screen top-0" />
      <form className=" w-full m-auto flex flex-col">
        <p className=" text-white text-5xl font-semibold text-center">
          Create Account
        </p>
        <br />
        <br />
        <br />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="email"
          placeholder="Email..."
          onChange={(e) => {
            setEmail(e.target.value);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="username"
          placeholder="Username..."
          onChange={(e) => {
            setUsernae(e.target.value);
            // console.log(email);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="tel"
          placeholder="Phone..."
          onChange={(e) => {
            setPhone(e.target.value);
            // console.log(email);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="password"
          placeholder="Password..."
          onChange={(e) => {
            setPassword(e.target.value);
            // console.log(email);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto  shadow-lg"
          type="password"
          placeholder="Repete Password..."
          onChange={(e) => {
            setRpassword(e.target.value);
            // console.log(email);
          }}
        />
        <p className="text-red-600  rounded-lg w-1/3 mx-auto text-center font-bold">
          {error}
        </p>
        <input
          className="w-1/12 p-2 m-2 rounded-full bg-primary-200 mx-auto shadow-lg text-white"
          type="button"
          value="Submit"
          onClick={submit_form}
        />
      </form>
    </div>
  );
}
