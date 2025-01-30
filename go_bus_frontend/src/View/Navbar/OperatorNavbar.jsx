import { Link } from "react-router-dom";
import "../user/Navbar.css";


export function OperatorNavbar() {
  return (
    <div style={{ marginLeft: "30px", marginRight: "30px" }}> 
     <nav className="navbar navbar-expand-lg shadow-sm rounde custom-navbar">
        <div className="container-fluid">
          <Link to="/">
            <img
              src="/img/GoBuslogo.png"
              alt="GoBus Logo"
              width="100"
              height="100"
              className="d-inline-block align-top"
            />
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarText"
            aria-controls="navbarText"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navbarText">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to="/OperatorHome" className="nav-link text-dark fs-5">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <a className="nav-link text-dark fs-5" href="#about">
                  About
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link text-dark fs-5" href="#">
                  Language
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link text-dark fs-5" href="#about">
                  Help
                </a>
              </li>
            </ul>

            <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link
                  to="/login"
                  className="btn btn-outline-primary fs-5 px-4 py-2 rounded-pill"
                >
                  Login/Signup
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}
