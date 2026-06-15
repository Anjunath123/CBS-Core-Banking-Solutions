// Branch F2 Lookup - Shared across all screens
// Shows BranchCode, BranchName, BranchType with pagination (5 per page)

var _branchLookupPage = 0;
var _branchLookupCallback = null;

function openBranchLookup(callback) {
    _branchLookupCallback = callback;
    _branchLookupPage = 0;
    var modal = document.getElementById('branchLookupModal');
    modal.classList.add('show');
    loadBranchLookupPage(0);
}

function closeBranchLookup() {
    document.getElementById('branchLookupModal').classList.remove('show');
}

function loadBranchLookupPage(page) {
    _branchLookupPage = page;
    var content = document.getElementById('branchLookupContent');
    content.innerHTML = 'Loading...';

    fetch(CTX + '/api/v1/admin/branch-master/lookup?page=' + page + '&size=5', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    }).then(function(res) { return res.json(); })
      .then(function(resp) {
          if (resp.data && resp.data.branches && resp.data.branches.length > 0) {
              var branches = resp.data.branches;
              var totalPages = resp.data.totalPages;
              var currentPage = resp.data.currentPage;

              var html = '<table><thead><tr><th>Branch Code</th><th>Branch Name</th><th>Branch Type</th></tr></thead><tbody>';
              branches.forEach(function(b) {
                  html += '<tr onclick="selectBranchFromLookup(\'' + b.branchCode + '\', \'' + (b.branchName || '') + '\')" style="cursor:pointer;">' +
                      '<td>' + b.branchCode + '</td>' +
                      '<td>' + (b.branchName || '') + '</td>' +
                      '<td>' + (b.branchType || '') + '</td></tr>';
              });
              html += '</tbody></table>';

              // Pagination controls
              html += '<div style="display:flex;justify-content:center;align-items:center;gap:12px;margin-top:12px;">';
              html += '<button class="btn btn-sm btn-outline" ' + (currentPage === 0 ? 'disabled' : '') + ' onclick="loadBranchLookupPage(' + (currentPage - 1) + ')">Prev</button>';
              html += '<span style="font-size:13px;">Page ' + (currentPage + 1) + ' of ' + totalPages + '</span>';
              html += '<button class="btn btn-sm btn-outline" ' + (currentPage >= totalPages - 1 ? 'disabled' : '') + ' onclick="loadBranchLookupPage(' + (currentPage + 1) + ')">Next</button>';
              html += '</div>';

              content.innerHTML = html;
          } else {
              content.innerHTML = '<p style="text-align:center;color:#718096;padding:20px;">No branches found</p>';
          }
      }).catch(function(err) {
          content.innerHTML = '<p style="color:#e53e3e;padding:20px;">Error loading branches</p>';
      });
}

function selectBranchFromLookup(branchCode, branchName) {
    if (_branchLookupCallback) {
        _branchLookupCallback(branchCode, branchName);
    }
    closeBranchLookup();
}

// F2 key handler for branch code
document.addEventListener('keydown', function(e) {
    if (e.key === 'F2') {
        var active = document.activeElement;
        if (active && active.id === 'branchCode') {
            e.preventDefault();
            openBranchLookup(function(code) {
                document.getElementById('branchCode').value = code;
            });
        }
    }
    if (e.key === 'Escape') {
        closeBranchLookup();
    }
});
