import React from "react";
import { NavLink } from "react-router-dom";
import '../styles/Navigation.css'

const Navigation = () => {
  return (
    <nav>
      <div>
        <NavLink to="/">
          Home
        </NavLink>
      </div>
      <div>
        <NavLink to="/community">
          Community
        </NavLink>
      </div>
      <div>
        <NavLink to="/playlist">
          Playlist
        </NavLink>
      </div>
      <div>
        <NavLink to="/findfriend">
          FindFriend
        </NavLink>
      </div>
      <div>
        <NavLink to = "/login">
         Login
        </NavLink>
      </div>
    </nav>
  );
};

export default Navigation;
