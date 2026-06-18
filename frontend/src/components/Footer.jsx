import React from "react";
import { Link } from "react-router-dom";
import {
  FaGithub,
  FaLinkedin,
  FaInstagram,
  FaXTwitter,
} from "react-icons/fa6";

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-gray-300 mt-auto">
      <div className="max-w-7xl mx-auto px-6 py-10">
        
        <div className="grid md:grid-cols-3 gap-8">
          
          {/* Brand Section */}
          <div>
            <h2 className="text-2xl font-bold text-white">
              ArtistReviewer
            </h2>

            <p className="mt-3 text-sm text-gray-400">
              Showcase your creativity, receive meaningful reviews,
              and connect with fellow artists and reviewers.
            </p>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-4">
              Quick Links
            </h3>

            <ul className="space-y-2">
              <li>
                <Link to="/home" className="hover:text-white transition">
                  Home
                </Link>
              </li>

              <li>
                <Link to="/projects" className="hover:text-white transition">
                  Projects
                </Link>
              </li>

              <li>
                <Link to="/add-project" className="hover:text-white transition">
                  Add Project
                </Link>
              </li>

              <li>
                <Link to="/dashboard" className="hover:text-white transition">
                  Dashboard
                </Link>
              </li>
            </ul>
          </div>

          {/* Social Links */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-4">
              Connect With Me
            </h3>

            <div className="flex gap-5 text-2xl">
              <a
                href="https://github.com/yourusername"
                target="_blank"
                rel="noreferrer"
                className="hover:text-white hover:scale-110 transition-transform"
              >
                <FaGithub />
              </a>

              <a
                href="https://linkedin.com/in/yourusername"
                target="_blank"
                rel="noreferrer"
                className="hover:text-white hover:scale-110 transition-transform"
              >
                <FaLinkedin />
              </a>

              <a
                href="https://x.com/yourusername"
                target="_blank"
                rel="noreferrer"
                className="hover:text-white hover:scale-110 transition-transform"
              >
                <FaXTwitter />
              </a>

              <a
                href="https://instagram.com/yourusername"
                target="_blank"
                rel="noreferrer"
                className="hover:text-white hover:scale-110 transition-transform"
              >
                <FaInstagram />
              </a>
            </div>
          </div>
        </div>

        {/* Bottom Section */}
        <div className="border-t border-gray-700 mt-8 pt-5 text-center text-sm text-gray-500">
          © {new Date().getFullYear()} ArtistReviewer. All Rights Reserved.
        </div>

      </div>
    </footer>
  );
};

export default Footer;