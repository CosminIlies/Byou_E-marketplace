/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        transparent_black: "#000000aa",
        primary: {
          100: "#00605b",
          200: "#00bf63",
        },
      },
    },
  },
  plugins: [],
};
