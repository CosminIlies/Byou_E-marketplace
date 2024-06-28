import { useParams } from "react-router-dom";
import Logo from "../Components/Logo";
import { useEffect, useState } from "react";
import { me } from "../Systems/LoginSystem";
import Cookies from "universal-cookie";
import UpdatePost from "./UpdatePost";

export default function Post() {
  const params = useParams();
  const [post, setPost] = useState({});
  const [successMsg, setSuccessMsg] = useState("");
  const [canEdit, setCanEdit] = useState(false);

  const [bought, setBought] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/products/${params.postId}`
      );
      const result = await response.json();

      const resp_seller_id = await fetch(
        `http://localhost:8080/products/${params.postId}/seller_id`
      );
      const res_seller_id = await resp_seller_id.text();

      await me().then((resp) => {
        if (resp.status === 200) {
          console.log(res_seller_id + " " + resp.text.id);
          if (res_seller_id == resp.text.id) setCanEdit(true);
        }
      });

      result.photo = `data:image/jpeg;base64,${result.photo}`;
      setPost(result);
    } catch (error) {
      console.error("Error fetching data", error);
    }
  };

  const constactSeller = async () => {
    const cookies = new Cookies();
    let buyer_id = 0;

    await me().then((resp) => {
      if (resp.status === 200) {
        console.log(resp.text.id);
        buyer_id = resp.text.id;
      }
    });

    console.log({ buyer_id: buyer_id });

    const response = await fetch(
      `http://localhost:8080/products/${params.postId}/buy`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + cookies.get("jwt-authorization"),
        },
        body: buyer_id,
      }
    );

    setSuccessMsg("An email to seller has been sent!");
    setBought(true);
  };

  return (
    <div className="snap-y snap-mandatory w-full no-scrollbar h-screen overflow-x-hidden overflow-y-scroll">
      <Logo />
      <div className="h-1/2 w-screen bg-primary-100 text-white text-5xl font-semibold text-center">
        <div className="w-1/2 h-full float-left">
          <img src={post.photo} className="w-1/3 h-full object-cover m-auto" />
        </div>
        <div className="w-1/2 h-full float-right overflow-y-scroll p-5">
          <p className="mb-8">{post.name}</p>

          <div className="w-full h-1/4 flex flex-row justify-evenly items-center">
            <div className="w-1/2">
              <p className="text-3xl ">{post.price}Lei</p>
            </div>

            <div className="w-1/2">
              {bought ? (
                <div>
                  <p className="text-primary-200 font-bold text-2xl">
                    An email to seller has been sent!
                  </p>
                </div>
              ) : (
                <button
                  className=" p-3 m-2 rounded-full text-2xl bg-primary-200  shadow-lg"
                  onClick={constactSeller}
                >
                  Contact Seller
                </button>
              )}
            </div>
          </div>

          <p className="text-lg">{post.description}</p>
        </div>
      </div>
    </div>
  );
}
