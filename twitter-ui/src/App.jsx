import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Register from "./components/Register";
import HomePage from "./pages/HomePage";
import ProfilePage from "./pages/ProfilePage";
import { useState, useEffect } from "react";

export default function App() {

  const [currentUser, setCurrentUser] = useState(() => {
    
    const storedUser = localStorage.getItem("currentUser");
    return storedUser ? JSON.parse(storedUser) : null;
  });

  useEffect(() => {
    
    if (currentUser) {
      localStorage.setItem("currentUser", JSON.stringify(currentUser));
    } else {
      localStorage.removeItem("currentUser");
    }
  }, [currentUser]);

  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<HomePage setCurrentUser={setCurrentUser} />} />
          <Route path="/register" element={<Register />} />
          <Route path="profile/:id" element={<ProfilePage currentUser={currentUser} />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}
