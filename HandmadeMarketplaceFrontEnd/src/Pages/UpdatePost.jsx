import Logo from "../Components/Logo";
import ScrollArrow from "../assets/scroll.svg";
import UpdatePostForm from "../Components/UpdatePostForm";

export default function UpdatePost({ post }) {
  return (
    <div className="snap-y snap-mandatory w-full no-scrollbar h-screen overflow-x-hidden overflow-y-scroll">
      <Logo />
      <div className="h-1/2 w-full flex flex-col justify-center bg-primary-100 text-white text-5xl font-semibold text-center">
        <p>Edit &apos;{post.name}&apos; post</p>
        <img src={ScrollArrow} className="w-1/12 mx-auto rotate-180" />
      </div>
      <UpdatePostForm post={post} />
    </div>
  );
}
