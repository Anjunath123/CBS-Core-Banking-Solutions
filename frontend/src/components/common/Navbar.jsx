import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav style={styles.nav}>
      <span style={styles.brand}>CanFin Core Banking</span>
      <div style={styles.right}>
        {user ? (
          <>
            <span style={styles.userInfo}>
              {user.username} &nbsp;|&nbsp;
              <strong>{user.role}</strong>
            </span>
            <button style={styles.logoutBtn} onClick={handleLogout}>Logout</button>
          </>
        ) : (
          <button style={styles.loginBtn} onClick={() => navigate("/login")}>Login</button>
        )}
      </div>
    </nav>
  );
}

const styles = {
  nav:      { display: "flex", justifyContent: "space-between", alignItems: "center", backgroundColor: "#003366", padding: "10px 24px" },
  brand:    { color: "#fff", fontWeight: "700", fontSize: "18px" },
  right:    { display: "flex", alignItems: "center", gap: "16px" },
  userInfo: { color: "#cce0ff", fontSize: "14px" },
  logoutBtn:{ backgroundColor: "#dc3545", color: "#fff", border: "none", padding: "6px 16px", borderRadius: "4px", cursor: "pointer" },
  loginBtn: { backgroundColor: "#28a745", color: "#fff", border: "none", padding: "6px 16px", borderRadius: "4px", cursor: "pointer" },
};

export default Navbar;
