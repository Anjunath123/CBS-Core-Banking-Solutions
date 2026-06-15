import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ActionButtons from "../components/common/ActionButtons";
import ROLES from "../constants/roles";

function Dashboard() {
  const { user } = useAuth();
  const navigate = useNavigate();

  if (!user) {
    navigate("/login");
    return null;
  }

  // Demo handlers — replace with real form/api calls in each page
  const handleSave    = () => alert("Save triggered");
  const handleUpdate  = () => alert("Update triggered");
  const handleView    = () => alert("View triggered");
  const handleReset   = () => alert("Reset triggered");
  const handleApprove = () => alert("Approve triggered");
  const handleReject  = () => alert("Reject triggered");

  return (
    <div style={styles.container}>
      <h3 style={styles.heading}>
        Welcome, {user.username} &nbsp;
        <span style={styles.roleBadge}>{user.role}</span>
      </h3>

      {/* Role-based navigation menu */}
      <div style={styles.menuGrid}>
        {user.role === ROLES.USER && (
          <>
            <MenuCard label="Customer"      onClick={() => navigate("/customer")} />
            <MenuCard label="Savings"       onClick={() => navigate("/savings")} />
            <MenuCard label="Deposit / FD"  onClick={() => navigate("/deposit")} />
            <MenuCard label="Loan Against FD" onClick={() => navigate("/loan-against-fd")} />
            <MenuCard label="Home Loan"     onClick={() => navigate("/home-loan")} />
          </>
        )}

        {user.role === ROLES.BRANCH_MANAGER && (
          <>
            <MenuCard label="Approve Loan Against FD" onClick={() => navigate("/loan-against-fd")} />
            <MenuCard label="Approve Home Loan"       onClick={() => navigate("/home-loan")} />
            <MenuCard label="Approve Deposit"         onClick={() => navigate("/deposit")} />
            <MenuCard label="Approve Savings"         onClick={() => navigate("/savings")} />
            <MenuCard label="Approve Customer"        onClick={() => navigate("/customer")} />
          </>
        )}
      </div>

      {/* Global action buttons — automatically shows correct set per role */}
      <ActionButtons
        onSave={handleSave}
        onUpdate={handleUpdate}
        onView={handleView}
        onReset={handleReset}
        onApprove={handleApprove}
        onReject={handleReject}
      />
    </div>
  );
}

function MenuCard({ label, onClick }) {
  return (
    <div style={cardStyles.card} onClick={onClick}>
      {label}
    </div>
  );
}

const styles = {
  container:  { padding: "24px" },
  heading:    { color: "#003366", marginBottom: "20px" },
  roleBadge:  { backgroundColor: "#003366", color: "#fff", padding: "2px 10px", borderRadius: "12px", fontSize: "12px" },
  menuGrid:   { display: "flex", flexWrap: "wrap", gap: "16px", marginBottom: "24px" },
};

const cardStyles = {
  card: {
    backgroundColor: "#fff", border: "1px solid #dce3ed", padding: "20px 28px",
    borderRadius: "8px", cursor: "pointer", fontWeight: "600", color: "#003366",
    boxShadow: "0 1px 4px rgba(0,0,0,0.08)", minWidth: "180px", textAlign: "center",
  },
};

export default Dashboard;
