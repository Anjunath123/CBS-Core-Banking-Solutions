var CTX = '/Kiya.aiCBS-10.2.0';

// Auth guard - redirect to login if no token
var token = localStorage.getItem('token');
if (!token) {
    window.location.href = CTX + '/login.html';
}

// Display logged-in user in sidebar
var userEl = document.getElementById('loggedInUser');
if (userEl) {
    var displayName = localStorage.getItem('fullName');
    if (!displayName) displayName = localStorage.getItem('username');
    if (displayName) userEl.textContent = displayName;
}

// Logout
function doLogout() {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('username');
    localStorage.removeItem('fullName');
    window.location.href = CTX + '/login.html';
}

// Refresh token (synchronous)
function tryRefreshToken() {
    var refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
        return false;
    }
    var xhr = new XMLHttpRequest();
    xhr.open('POST', CTX + '/api/v1/auth/refresh', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    try {
        xhr.send(JSON.stringify({ refreshToken: refreshToken }));
    } catch (e) {
        return false;
    }
    if (xhr.status === 200) {
        var data = JSON.parse(xhr.responseText);
        if (data.data && data.data.token) {
            localStorage.setItem('token', data.data.token);
            if (data.data.refreshToken) {
                localStorage.setItem('refreshToken', data.data.refreshToken);
            }
            return true;
        }
    }
    return false;
}

// Override global fetch - automatically adds token, handles 401 refresh, handles 403 logout
var _originalFetch = window.fetch;

window.fetch = function(url, options) {
    if (!options) {
        options = {};
    }
    if (!options.headers) {
        options.headers = {};
    }

    // Skip auth header for auth endpoints
    var isAuthUrl = (typeof url === 'string') && url.indexOf('/api/v1/auth/') !== -1;
    if (!isAuthUrl) {
        options.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
    }

    return _originalFetch(url, options).then(function(response) {
        if (isAuthUrl) {
            return response;
        }
        if (response.status === 401) {
            var refreshed = tryRefreshToken();
            if (refreshed) {
                options.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
                return _originalFetch(url, options);
            } else {
                doLogout();
                return response;
            }
        }
        if (response.status === 403) {
            // Don't logout - show access denied message
            showAccessDenied();
            return response;
        }
        return response;
    });
};

// Show access denied toast/alert
function showAccessDenied() {
    var toast = document.getElementById('toast');
    if (toast) {
        toast.textContent = 'Access Denied: You don\'t have permission for this action';
        toast.className = 'toast toast-error';
        toast.style.display = 'block';
        setTimeout(function() { toast.style.display = 'none'; }, 4000);
    } else {
        alert('Access Denied: You don\'t have permission for this action');
    }
}

// Keep authFetch as alias for backward compatibility
function authFetch(url, options) {
    return window.fetch(url, options);
}
