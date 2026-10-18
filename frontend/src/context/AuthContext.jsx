import { createContext, useContext, useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { login as apiLogin, logout as apiLogout } from "../services/authService";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null); // { username, role }

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode(token);
        // Backend sets role as "ROLE_USER" or "ROLE_BRANCH_MANAGER"
        const role = (decoded.role || decoded.roles || "")
          .replace("ROLE_", "");
        setUser({ username: decoded.sub, role });
      } catch {
        apiLogout();
      }
    }
  }, []);

  const login = async (username, password) => {
    const token = await apiLogin(username, password);
    const decoded = jwtDecode(token);
    const role = (decoded.role || decoded.roles || "").replace("ROLE_", "");
    setUser({ username: decoded.sub, role });
  };

  const logout = () => {
    apiLogout();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
