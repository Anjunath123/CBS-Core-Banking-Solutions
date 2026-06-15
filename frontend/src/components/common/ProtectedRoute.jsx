import { Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

/**
 * allowedRoles: optional array e.g. ["USER"] or ["BRANCH_MANAGER"]
 * If empty/undefined → any authenticated user can access
 */
function ProtectedRoute({ children, allowedRoles }) {
  const { user } = useAuth();

  if (!user) return <Navigate to="/login" replace />;

  if (allowedRoles && !allowedRoles.includes(user.role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
}

export default ProtectedRoute;
