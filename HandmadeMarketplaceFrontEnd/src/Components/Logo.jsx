import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Hamburger from "./Hamburger";
import LogoImg from "../assets/bYou2.png";
import Cookies from "universal-cookie";
import { me } from "../Systems/LoginSystem";

export default function Logo() {
  const [isMenuActivated, setIsMenuactivated] = useState(false);
  const [username, setUsername] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const cookies = new Cookies();

  useEffect(() => {
    me().then((resp) => {
      if (resp.status === 200) {
        setIsLoggedIn(true);
        setUsername(resp.text.username);
        console.log(resp.text);
      }
    });
  }, []);

  const logout = () => {
    cookies.remove("jwt-authorization");
    setIsLoggedIn(false);
    setUsername("");
    // window.location.reload(false);
  };

  return (
    <div className="flex p-20 h-1/2 w-full bg-gradient-to-b from-primary-200 to-primary-100 snap-start">
      <button
        className="h-fit w-fit"
        onClick={() => {
          setIsMenuactivated(!isMenuActivated);
        }}
      >
        <Hamburger />
      </button>
      {isMenuActivated ? (
        <ul className="absolute top-0 left-0 p-20 rounded-br-lg bg-gradient-to-b to-primary-200 from-[#00bf63] shadow-2xl text-white text-xl">
          <li className="flex">
            <button
              className="h-fit w-fit z-10"
              onClick={() => {
                setIsMenuactivated(!isMenuActivated);
              }}
            >
              <Hamburger />
            </button>

            <Link
              to="/"
              className="ml-5 text-white text-4xl font-semibold font-sans"
            >
              <img src={LogoImg} className="w-20" />
            </Link>
          </li>
          {isLoggedIn ? (
            <li className="text-2xl font-semibold">Welcome, {username}</li>
          ) : (
            <></>
          )}

          <li>
            <Link to="/">Home</Link>
          </li>
          {!isLoggedIn ? (
            <>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/register">Register</Link>
              </li>
            </>
          ) : (
            <>
              <li>
                <Link to="/makenewpost">Make new post</Link>
              </li>
              <li>
                <button onClick={logout}>Logout</button>
              </li>
            </>
          )}
        </ul>
      ) : (
        <></>
      )}

      <Link to="/" className="ml-5 text-white text-4xl font-semibold font-sans">
        <img src={LogoImg} className="w-20" />
      </Link>
    </div>
  );
}
