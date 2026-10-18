import { useAuth } from "../../context/AuthContext";
import LABELS from "../../constants/labels";
import ROLES from "../../constants/roles";

/**
 * Global Action Buttons — role-driven rendering
 *
 * USER           → Save | Update | View | Reset
 * BRANCH_MANAGER → Approve | Reject | View
 *
 * Props:
 *   onSave, onUpdate, onView, onReset   — USER handlers
 *   onApprove, onReject                 — BRANCH_MANAGER handlers
 */
function ActionButtons({ onSave, onUpdate, onView, onReset, onApprove, onReject }) {
  const { user } = useAuth();

  if (!user) return null; // Not logged in — show nothing

  const isUser           = user.role === ROLES.USER;
  const isBranchManager  = user.role === ROLES.BRANCH_MANAGER;

  return (
    <div style={styles.container}>
      {/* ── USER buttons ── */}
      {isUser && (
        <>
          <button style={{ ...styles.btn, ...styles.save }}   onClick={onSave}>
            {LABELS.SAVE}
          </button>
          <button style={{ ...styles.btn, ...styles.update }} onClick={onUpdate}>
            {LABELS.UPDATE}
          </button>
          <button style={{ ...styles.btn, ...styles.view }}   onClick={onView}>
            {LABELS.VIEW}
          </button>
          <button style={{ ...styles.btn, ...styles.reset }}  onClick={onReset}>
            {LABELS.RESET}
          </button>
        </>
      )}

      {/* ── BRANCH_MANAGER buttons ── */}
      {isBranchManager && (
        <>
          <button style={{ ...styles.btn, ...styles.approve }} onClick={onApprove}>
            {LABELS.APPROVE}
          </button>
          <button style={{ ...styles.btn, ...styles.reject }}  onClick={onReject}>
            {LABELS.REJECT}
          </button>
          <button style={{ ...styles.btn, ...styles.view }}    onClick={onView}>
            {LABELS.VIEW}
          </button>
        </>
      )}
    </div>
  );
}

const styles = {
  container: { display: "flex", gap: "10px", padding: "12px 0" },
  btn:    { padding: "8px 20px", borderRadius: "4px", border: "none", cursor: "pointer", fontWeight: "600", color: "#fff" },
  save:   { backgroundColor: "#28a745" },
  update: { backgroundColor: "#007bff" },
  view:   { backgroundColor: "#6c757d" },
  reset:  { backgroundColor: "#fd7e14" },
  approve:{ backgroundColor: "#20c997" },
  reject: { backgroundColor: "#dc3545" },
};

export default ActionButtons;
