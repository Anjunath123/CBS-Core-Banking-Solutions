import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Navbar from "./components/common/Navbar";
import ProtectedRoute from "./components/common/ProtectedRoute";
import LoginPage   from "./pages/LoginPage";
import Dashboard   from "./pages/Dashboard";
import ROLES from "./constants/roles";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          {/* Public */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/"      element={<Navigate to="/dashboard" replace />} />

          {/* USER + BRANCH_MANAGER can view dashboard */}
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute allowedRoles={[ROLES.USER, ROLES.BRANCH_MANAGER]}>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          {/* USER only pages */}
          <Route
            path="/customer"
            element={
              <ProtectedRoute allowedRoles={[ROLES.USER, ROLES.BRANCH_MANAGER]}>
                {/* <CustomerPage /> — add when ready */}
                <div style={{ padding: 24 }}>Customer Page (Coming Soon)</div>
              </ProtectedRoute>
            }
          />

          <Route
            path="/loan-against-fd"
            element={
              <ProtectedRoute allowedRoles={[ROLES.USER, ROLES.BRANCH_MANAGER]}>
                {/* <LoanAgainstFDPage /> — add when ready */}
                <div style={{ padding: 24 }}>Loan Against FD Page (Coming Soon)</div>
              </ProtectedRoute>
            }
          />

          <Route path="/unauthorized" element={<div style={{ padding: 24, color: "red" }}>Access Denied — Insufficient Role</div>} />
          <Route path="*"            element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
