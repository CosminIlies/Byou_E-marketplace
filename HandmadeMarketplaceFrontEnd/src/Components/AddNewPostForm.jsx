import { useState } from "react";
import Cookies from "universal-cookie";
import BgPattern from "../assets/pattern.svg";

export default function AddNewPostForm() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState(0.0);
  const [amount, setAmount] = useState(0);
  const [photo, setPhoto] = useState(null);

  const submit_form = async () => {
    const cookies = new Cookies();
    let id = 0;

    await fetch("http://localhost:8080/me", {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + cookies.get("jwt-authorization"),
      },
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        id = data.id;
      })
      .catch((err) => {
        console.log(err);
        return;
      });

    const formData = new FormData();
    formData.append("name", name);
    formData.append("description", description);
    formData.append("price", price);
    formData.append("amount", amount);
    formData.append("photo", photo);

    const response = await fetch(
      "http://localhost:8080/accounts/" + id + "/products",
      {
        method: "POST",
        headers: {
          Authorization: "Bearer " + cookies.get("jwt-authorization"),
        },
        body: formData,
      }
    );

    if (response.status !== 201) window.location.reload(false);
    else window.location.href = "/";
  };

  return (
    <div className="flex flex-col relative m-auto h-screen w-full snap-start">
      <img src={BgPattern} className="absolute -z-10 w-screen top-0" />
      <form className=" w-full m-auto flex flex-col">
        <p className=" text-white text-5xl font-semibold text-center">
          Add new post:
        </p>
        <br />
        <br />
        <br />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="text"
          placeholder="Name..."
          onChange={(e) => {
            setName(e.target.value);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="text"
          placeholder="Description..."
          onChange={(e) => {
            setDescription(e.target.value);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="number"
          placeholder="Price..."
          onChange={(e) => {
            setPrice(e.target.value);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="number"
          placeholder="Amount..."
          onChange={(e) => {
            setAmount(e.target.value);
          }}
        />

        <input
          className="w-1/3 p-2 m-2 rounded-full mx-auto shadow-lg"
          type="file"
          placeholder="Photo..."
          onChange={(e) => {
            setPhoto(e.target.files[0]);
          }}
        />

        <input
          className="w-1/12 p-2 m-2 rounded-full bg-primary-200 mx-auto shadow-lg text-white"
          type="button"
          value="Submit"
          onClick={submit_form}
        ></input>
      </form>
    </div>
  );
}
