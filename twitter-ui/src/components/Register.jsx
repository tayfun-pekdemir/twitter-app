import { Link,useNavigate} from "react-router-dom";
import { useState } from "react";
import axios from "axios";

export default function Register() {

    let initialFormData = {
        firstName:"",
        lastName:"",
        userName:"",
        email:"",
        password:""
    }

    const [formData,setFormData] = useState(initialFormData);
    const [error,setError] = useState("");

    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({...formData,[e.target.name]:e.target.value});
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post("http://localhost:3000/auth/register", formData,{ withCredentials: true })
        .then(response => {
            console.log(response.data);
            navigate("/");
        })
        .catch(error => {
            console.error(error);
            setError(error.response.data.message || "Registration Failed.");
        });
    }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-[#141619] px-4">
      <div className="w-full max-w-sm bg-[#2C2E3A] p-6 rounded-xl shadow-lg shadow-black/50">
        <h2 className="text-2xl font-semibold text-center mb-6 text-[#B3B4BD]">
          Register
        </h2>
         {error && <p className="text-red-500 text-sm text-center mb-2">{error}</p>}
        <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
          <input
            type="text"
            name="firstName"
            onChange={handleChange}
            value={formData.firstName}
            placeholder="First name"
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
          />

          <input
            type="text"
            name="lastName"
            onChange={handleChange}
            value={formData.lastName}
            placeholder="Last name"
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
          />

          <input
            type="text"
            name="userName"
            onChange={handleChange}
            value={formData.userName}
            placeholder="Username"
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
          />

          <input
            type="email"
            name="email"
            onChange={handleChange}
            value={formData.email}
            placeholder="Email"
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
          />

          <input
            type="password"
            name="password"
            onChange={handleChange}
            value={formData.password}
            placeholder="Password (min 6 characters)"
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md shadow-lg shadow-black/50 px-4 py-2 w-full
                       focus:outline-none focus:ring-2 focus:ring-[#0A21C0]"
          />

          <button
            type="submit"
            className="bg-[#050A44] text-[#B3B4BD] font-semibold py-2 shadow-lg shadow-black/50 rounded-md
                       hover:bg-[#070e5a] transition-colors duration-200"
          >
            Register
          </button>

          <div className="flex flex-col sm:flex-row justify-center items-center gap-2 mt-2 text-sm">
            <span className="text-[#B3B4BD]">Already have an account?</span>
            <Link to="/" className="text-[#0A21C0] hover:underline">
              Login here
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
