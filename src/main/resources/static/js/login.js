var CTX = '/Kiya.aiCBS-10.2.0';
const API_BASE = CTX + '/api/v1/auth';

// If already logged in, redirect to customer page
if (localStorage.getItem('token')) {
    window.location.href = CTX + '/customer';
}

function showToast(msg, type) {
    const toast = document.getElementById('toast');
    toast.textContent = msg;
    toast.className = 'toast toast-' + type;
    toast.style.display = 'block';
    setTimeout(() => toast.style.display = 'none', 4000);
}

function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
}

// Login
async function doLogin() {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    if (!username || !password) { showToast('Enter username and password', 'error'); return; }

    try {
        const res = await fetch(API_BASE + '/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        const data = await res.json();
        if (res.ok && data.data) {
            localStorage.setItem('token', data.data.token);
            localStorage.setItem('refreshToken', data.data.refreshToken);
            localStorage.setItem('username', data.data.username);
            localStorage.setItem('fullName', data.data.fullName);
            showToast('Login successful!', 'success');
            setTimeout(() => window.location.href = CTX + '/customer', 500);
        } else {
            showToast(data.message || data.errorMessage || 'Login failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Register
async function doRegister() {
    const form = document.getElementById('registerForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const payload = {
        fullName: document.getElementById('regFullName').value,
        email: document.getElementById('regEmail').value,
        username: document.getElementById('regUsername').value,
        password: document.getElementById('regPassword').value
    };

    try {
        const res = await fetch(API_BASE + '/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Registration successful! Please login.', 'success');
            form.reset();
            setTimeout(() => showSection('loginSection'), 1500);
        } else {
            showToast(data.message || data.errorMessage || 'Registration failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Forgot Password
async function doForgotPassword() {
    const username = document.getElementById('forgotUsername').value;
    if (!username) { showToast('Enter your username', 'error'); return; }

    try {
        const res = await fetch(API_BASE + '/forgot-password', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username })
        });
        const data = await res.json();
        if (res.ok) {
            // Show OTP (in production this would be sent via email)
            const otpMatch = data.data ? data.data.match(/\d{6}/) : null;
            if (otpMatch) {
                document.getElementById('otpDisplay').style.display = 'block';
                document.getElementById('otpDisplay').innerHTML =
                    '<p style="font-size:12px;color:#555;margin-bottom:6px;">Your OTP (demo only):</p>' +
                    '<div class="otp-display">' + otpMatch[0] + '</div>';
            }
            showToast('OTP generated successfully!', 'success');
            // Move to reset section after short delay
            document.getElementById('resetUsername').value = username;
            setTimeout(() => showSection('resetSection'), 2000);
        } else {
            showToast(data.message || data.errorMessage || 'Failed to send OTP', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Reset Password
async function doResetPassword() {
    const form = document.getElementById('resetForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const newPassword = document.getElementById('resetNewPassword').value;
    const confirmPassword = document.getElementById('resetConfirmPassword').value;
    if (newPassword !== confirmPassword) {
        showToast('Passwords do not match', 'error');
        return;
    }

    const payload = {
        username: document.getElementById('resetUsername').value,
        otp: document.getElementById('resetOtp').value,
        newPassword: newPassword
    };

    try {
        const res = await fetch(API_BASE + '/reset-password', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Password reset successful! Please login.', 'success');
            form.reset();
            setTimeout(() => showSection('loginSection'), 1500);
        } else {
            showToast(data.message || data.errorMessage || 'Reset failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Allow Enter key to submit login
document.getElementById('loginPassword').addEventListener('keydown', function(e) {
    if (e.key === 'Enter') doLogin();
});
