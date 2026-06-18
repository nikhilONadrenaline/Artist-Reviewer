import React from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";

const Header = () => {

  const navLinkStyles = ({ isActive }) =>
    `px-4 py-2 rounded-md transition-all duration-200 ${
      isActive
        ? "bg-blue-600 text-white"
        : "text-gray-700 hover:bg-gray-100"
    }`;

    const navigate = useNavigate();

     const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    navigate("/");
  };
  return (
    <header className="bg-white shadow-md">
      <nav className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
        
        {/* Logo */}
        <Link
          to="/home"
          className="text-2xl font-bold text-blue-600"
        >
          ArtistReviewer
        </Link>

        {/* Navigation Links */}
        <div className="flex items-center gap-4">
          <NavLink to="/home" className={navLinkStyles}>
            Home
          </NavLink>

          <NavLink to="/projects" className={navLinkStyles}>
            Projects
          </NavLink>

          <NavLink to="/add-project" className={navLinkStyles}>
            Add Project
          </NavLink>

          <NavLink to="/dashboard" className={navLinkStyles}>
            Dashboard
          </NavLink>

          <button
            onClick={handleLogout}
            className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition"
          >
            Logout
          </button>
        </div>
      </nav>
    </header>
  );
};

export default Header;