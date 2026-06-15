// Roles must match backend Spring Security role values (without ROLE_ prefix)
const ROLES = {
  USER:           "USER",           // Maker  — Save, Update, View, Reset
  BRANCH_MANAGER: "BRANCH_MANAGER", // Checker — Approve, Reject + View
};

export default ROLES;
