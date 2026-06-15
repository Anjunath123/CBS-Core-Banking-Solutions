var CTX = '/Kiya.aiCBS-10.2.0';
var API_BASE = CTX + '/api/v1/admin/branch-master';
var editMode = false;

// Tab switching
document.querySelectorAll('.tab').forEach(function(tab) {
    tab.addEventListener('click', function() {
        document.querySelectorAll('.tab').forEach(function(t) { t.classList.remove('active'); });
        document.querySelectorAll('.tab-content').forEach(function(c) { c.classList.remove('active'); });
        tab.classList.add('active');
        document.getElementById('tab-' + tab.getAttribute('data-tab')).classList.add('active');
        if (tab.getAttribute('data-tab') === 'list') loadBranches();
    });
});

function toggleSubmenu() {
    var submenu = document.getElementById('adminSubmenu');
    submenu.style.display = submenu.style.display === 'none' ? 'block' : 'block';
}

function showToast(msg, type) {
    var toast = document.getElementById('toast');
    toast.textContent = msg;
    toast.className = 'toast toast-' + type;
    toast.style.display = 'block';
    setTimeout(function() { toast.style.display = 'none'; }, 3000);
}

function getFormData() {
    return {
        branchCode: document.getElementById('branchCode').value.trim(),
        branchName: document.getElementById('branchName').value.trim(),
        branchType: document.getElementById('branchType').value,
        ifscCode: document.getElementById('ifscCode').value.trim(),
        micrCode: document.getElementById('micrCode').value.trim(),
        branchEmailId: document.getElementById('branchEmailId').value.trim(),
        buildingName: document.getElementById('buildingName').value.trim(),
        streetName: document.getElementById('streetName').value.trim(),
        landmark: document.getElementById('landmark').value.trim(),
        localityName: document.getElementById('localityName').value.trim(),
        countryCode: document.getElementById('countryCode').value.trim()
    };
}

function saveBranch() {
    var data = getFormData();
    if (!data.branchCode || !data.branchName) {
        showToast('Branch Code and Branch Name are required', 'error');
        return;
    }

    var method = editMode ? 'PUT' : 'POST';
    fetch(API_BASE, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(function(res) { return res.json(); })
      .then(function(resp) {
          if (resp.successMessage) {
              showToast(resp.successMessage, 'success');
              resetBranchForm();
          } else {
              showToast(resp.errorMessage || 'Error saving branch', 'error');
          }
      }).catch(function(err) { showToast('Error: ' + err.message, 'error'); });
}

function loadBranches() {
    fetch(API_BASE, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then(function(res) { return res.json(); })
        .then(function(resp) {
            var tbody = document.getElementById('branchListBody');
            tbody.innerHTML = '';
            var branches = resp.data || [];
            branches.forEach(function(b) {
                var tr = document.createElement('tr');
                tr.innerHTML = '<td>' + b.branchCode + '</td>' +
                    '<td>' + b.branchName + '</td>' +
                    '<td>' + (b.branchType || '') + '</td>' +
                    '<td>' + (b.ifscCode || '') + '</td>' +
                    '<td>' + (b.branchEmailId || '') + '</td>' +
                    '<td><button class="btn btn-primary btn-sm" onclick="editBranch(\'' + b.branchCode + '\')">Edit</button> ' +
                    '<button class="btn btn-danger btn-sm" onclick="deleteBranch(\'' + b.branchCode + '\')">Delete</button></td>';
                tbody.appendChild(tr);
            });
        }).catch(function(err) { showToast('Error loading branches', 'error'); });
}

function editBranch(branchCode) {
    fetch(API_BASE + '/' + branchCode, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then(function(res) { return res.json(); })
        .then(function(resp) {
            var b = resp.data;
            document.getElementById('branchCode').value = b.branchCode;
            document.getElementById('branchCode').readOnly = true;
            document.getElementById('branchName').value = b.branchName || '';
            document.getElementById('branchType').value = b.branchType || '';
            document.getElementById('ifscCode').value = b.ifscCode || '';
            document.getElementById('micrCode').value = b.micrCode || '';
            document.getElementById('branchEmailId').value = b.branchEmailId || '';
            document.getElementById('buildingName').value = b.buildingName || '';
            document.getElementById('streetName').value = b.streetName || '';
            document.getElementById('landmark').value = b.landmark || '';
            document.getElementById('localityName').value = b.localityName || '';
            document.getElementById('countryCode').value = b.countryCode || '';
            editMode = true;
            // Switch to create tab
            document.querySelectorAll('.tab').forEach(function(t) { t.classList.remove('active'); });
            document.querySelectorAll('.tab-content').forEach(function(c) { c.classList.remove('active'); });
            document.querySelector('[data-tab="create"]').classList.add('active');
            document.getElementById('tab-create').classList.add('active');
        }).catch(function(err) { showToast('Error fetching branch', 'error'); });
}

function deleteBranch(branchCode) {
    if (!confirm('Are you sure you want to delete branch: ' + branchCode + '?')) return;
    fetch(API_BASE + '/' + branchCode, { method: 'DELETE', headers: { 'Content-Type': 'application/json' } })
        .then(function(res) { return res.json(); })
        .then(function(resp) {
            showToast(resp.successMessage || 'Branch Deleted', 'success');
            loadBranches();
        }).catch(function(err) { showToast('Error deleting branch', 'error'); });
}

function resetBranchForm() {
    document.getElementById('branchForm').reset();
    document.getElementById('branchCode').readOnly = false;
    editMode = false;
}
