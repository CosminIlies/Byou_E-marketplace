import Logo from "../Components/Logo";
import CreateAccountForm from "../Components/CreateAccountForm";
import ScrollArrow from "../assets/scroll.svg";
import LoginForm from "../Components/LoginForm";

export default function Login() {
  return (
    <div className="snap-y snap-mandatory w-full no-scrollbar h-screen overflow-x-hidden overflow-y-scroll">
      <Logo />
      <div className="h-1/2 w-full flex flex-col justify-center bg-primary-100 text-white text-5xl font-semibold text-center">
        <p>Log In</p>
        <img src={ScrollArrow} className="w-1/12 mx-auto rotate-180" />
      </div>
      <LoginForm />
    </div>
  );
}
