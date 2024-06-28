import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";

import "cors";
import Home from "./Pages/Home.jsx";
import Login from "./Pages/Login.jsx";
import CreateAccount from "./Pages/CreateAccount.jsx";
import Post from "./Pages/Post.jsx";
import AddNewPost from "./Pages/AddNewPost.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    Component: Home,
    errorElement: <h1>404 NOT_FOUND</h1>,
  },
  {
    path: "/login",
    Component: Login,
  },
  {
    path: "/register",
    Component: CreateAccount,
  },
  {
    path: "/post/:postId",
    Component: Post,
  },
  {
    path: "/makenewpost",
    Component: AddNewPost,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
