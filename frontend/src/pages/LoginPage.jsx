import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ROLES from "../constants/roles";

function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await login(form.username, form.password);
      // Redirect based on role after login
      navigate("/dashboard");
    } catch {
      setError("Invalid username or password");
    }
  };

  return (
    <div style={styles.wrapper}>
      <div style={styles.card}>
        <h2 style={styles.title}>CanFin Core Banking</h2>
        <form onSubmit={handleSubmit}>
          <input
            style={styles.input}
            placeholder="Username"
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
            required
          />
          <input
            style={styles.input}
            type="password"
            placeholder="Password"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            required
          />
          {error && <p style={styles.error}>{error}</p>}
          <button style={styles.btn} type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}

const styles = {
  wrapper: { display: "flex", justifyContent: "center", alignItems: "center", height: "100vh", backgroundColor: "#f0f4f8" },
  card:    { backgroundColor: "#fff", padding: "40px", borderRadius: "8px", boxShadow: "0 2px 12px rgba(0,0,0,0.15)", minWidth: "320px" },
  title:   { textAlign: "center", color: "#003366", marginBottom: "24px" },
  input:   { width: "100%", padding: "10px", marginBottom: "14px", borderRadius: "4px", border: "1px solid #ccc", boxSizing: "border-box" },
  btn:     { width: "100%", padding: "10px", backgroundColor: "#003366", color: "#fff", border: "none", borderRadius: "4px", cursor: "pointer", fontWeight: "700" },
  error:   { color: "red", fontSize: "13px", marginBottom: "8px" },
};

export default LoginPage;
