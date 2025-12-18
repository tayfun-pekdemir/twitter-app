import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Login({setCurrentUser}) {

  const initialFormData = {
    email:"",
    password:""
  }

  const navigate = useNavigate();

  const [formData,setFormData] = useState(initialFormData);
  const [error,setError] = useState("")

  const handleChange = (e) => {

    setFormData({...formData,[e.target.name]:e.target.value});
  }

  const handleSubmit = (e) => {

    e.preventDefault();

    axios.post("http://localhost:3000/auth/login",formData,{withCredentials:true})
      .then(response => {
        console.log(response.data);
        setCurrentUser(response.data.user);
        localStorage.setItem("currentUser", JSON.stringify(response.data.user));
        navigate(`/profile/${response.data.user.id}`)
      })
      .catch(error => {
        console.error(error);
        setError(error.response.data.message || "Login error");
      })
  }
    
  return (
    <div className="w-full max-w-sm bg-[#2C2E3A] p-6 rounded-xl shadow-lg shadow-black/50">
      <h2 className="text-2xl font-semibold text-center mb-6 text-[#B3B4BD]">
        Login
      </h2>
      {error && <p className="text-red-500 text-sm text-center mb-2">{error}</p>}
      <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
        <input
          type="email"
          name="email"
          value={formData.email}
          placeholder="Email"
          onChange={handleChange}
          className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
        />

        <input
          type="password"
          name="password"
          value={formData.password}
          placeholder="Password"
          onChange={handleChange}
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
