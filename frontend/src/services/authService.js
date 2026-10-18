import api from "../api/axiosConfig";

export const login = async (username, password) => {
  const res = await api.post("/auth/login", { username, password });
  const { token, refreshToken } = res.data.data;
  localStorage.setItem("token", token);
  localStorage.setItem("refreshToken", refreshToken);
  return token;
};

export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
};
