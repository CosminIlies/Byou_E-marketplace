import { useState } from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

export default function PostMiniature({ post }) {
  const [hovered, setHovered] = useState(false);
  if (post == undefined) return <div className=" w-1/5"></div>;

  if (!hovered) {
    return (
      <div
        className="bg-red-950 w-1/5"
        onMouseEnter={() => setHovered(true)}
        onMouseLeave={() => setHovered(false)}
      >
        <img src={post.image} className="w-full h-full object-cover" />
        <p className="relative bottom-12 text-white text-4xl font-sans font-semibold float-right mx-4">
          {post.price} Lei
        </p>
      </div>
    );
  } else {
    return (
      <Link
        to={`/post/${post.id}`}
        key={post.id}
        className="bg-red-950 w-1/5"
        onMouseEnter={() => setHovered(true)}
        onMouseLeave={() => setHovered(false)}
      >
        <img src={post.image} className="w-full h-full object-cover" />
        <div className="relative -top-full left-0 w-full h-full bg-transparent_black">
          <p className="relative text-4xl font-sans font-semibold p-10 text-center text-white">
            {post.title}
          </p>
          <p className="relative text-4xl font-sans font-semibold p-10 float-right  text-white">
            {post.price} Lei
          </p>
        </div>
      </Link>
    );
  }
}

PostMiniature.propTypes = {
  post: PropTypes.exact({
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    image: PropTypes.string.isRequired,
  }),
};
