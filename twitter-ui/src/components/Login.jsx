import { Link } from "react-router-dom";

export default function Login() {

    
  return (
    <div className="w-full max-w-sm bg-[#2C2E3A] p-6 rounded-xl shadow-lg shadow-black/50">
      <h2 className="text-2xl font-semibold text-center mb-6 text-[#B3B4BD]">
        Login
      </h2>

      <form className="flex flex-col gap-4">
        <input
          type="email"
          placeholder="Email"
          className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
        />

        <input
          type="password"
          placeholder="Password"
          className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
        />

        <button
          type="submit"
          className="bg-[#050A44] text-[#B3B4BD] font-semibold py-2 shadow-lg shadow-black/50 rounded-md
                       hover:bg-[#070e5a] transition-colors duration-200"
        >
          Login
        </button>

        <div className="flex flex-col sm:flex-row justify-center items-center gap-2 mt-2 text-sm">
          <span className="text-[#B3B4BD]">Don't have an account?</span>
          <Link to="/register" className="text-[#0A21C0] hover:underline">
            Register here
          </Link>
        </div>
      </form>
    </div>
  );
}
