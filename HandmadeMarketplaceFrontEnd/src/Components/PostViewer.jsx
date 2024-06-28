import { useState, useEffect } from "react";
import PostMiniature from "./PostMiniature";
import img2 from "../assets/img_test_1.jpg";
// import img2 from "../assets/img_test_2.jpg";
import Arrow from "./Arrow";

export default function PostViewer() {
  const [index, setIndex] = useState(0);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/products");
      const result = await response.json();
      const postFetched = [];

      result.map((product) => {
        if (!product.photo) return;
        postFetched.push({
          id: product.id,
          title: product.name,
          price: product.price,
          image: `data:image/jpeg;base64,${product.photo}`,
        });
      });

      setPosts(postFetched);
    } catch (error) {
      console.error("Error fetching data", error);
    }
  };

  return (
    <div className="flex left-0 bottom-0 h-1/2 w-full bg-primary-100 justify-between overflow-hidden">
      {posts.length > 0 ? <PostMiniature post={posts[index]} /> : <></>}

      <button
        onClick={() => {
          if (index > 0) setIndex(index - 1);
        }}
        className="absolute top-2/3 left-5 bottom-1/2"
      >
        <Arrow />
      </button>

      {posts.length > 0 ? <PostMiniature post={posts[index + 1]} /> : <></>}
      {posts.length > 0 ? <PostMiniature post={posts[index + 2]} /> : <></>}
      {posts.length > 0 ? <PostMiniature post={posts[index + 3]} /> : <></>}
      <button
        onClick={() => {
          if (index < posts.length - 1) setIndex(index + 1);
        }}
        className="absolute top-2/3 right-5 rotate-180"
      >
        <Arrow />
      </button>
    </div>
  );
}
