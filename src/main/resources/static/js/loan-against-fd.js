const API_BASE = CTX + '/api/v1/loan-against-fd';

// Tab switching
document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        tab.classList.add('active');
        document.getElementById('tab-' + tab.dataset.tab).classList.add('active');
    });
});

function showToast(msg, type) {
    const toast = document.getElementById('toast');
    toast.textContent = msg;
    toast.className = 'toast toast-' + type;
    toast.style.display = 'block';
    setTimeout(() => toast.style.display = 'none', 4000);
}

async function createLoan() {
    const form = document.getElementById('loanForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const disbDateVal = document.getElementById('disbursementDate').value;
    const payload = {
        branchCode: parseInt(document.getElementById('branchCode').value),
        customerId: parseInt(document.getElementById('customerId').value),
        accountName: document.getElementById('accountName').value,
        fdAccountNumber: document.getElementById('fdAccountNumber').value,
        loanAmount: parseFloat(document.getElementById('loanAmount').value),
        currencyCode: document.getElementById('currencyCode').value,
        loanTenureMonths: parseInt(document.getElementById('loanTenureMonths').value),
        disbursementDate: disbDateVal ? new Date(disbDateVal).toISOString() : null,
        loanPurpose: document.getElementById('loanPurpose').value,
        repaymentMode: document.getElementById('repaymentMode').value,
        repaymentAccountNumber: document.getElementById('repaymentAccountNumber').value,
        remarks: document.getElementById('remarks').value || '',
        intRate: 0
    };

    try {
        const res = await authFetch(API_BASE + '/create', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan created! Account: ' + data.data.loanAccountNumber, 'success');
            form.reset();
        } else {
            showToast(data.message || data.errorMessage || 'Failed to create loan', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function approveLoan() {
    const loanAccountNumber = document.getElementById('approveLoanAccNo').value;
    const branchCode = document.getElementById('approveBranchCode').value;
    if (!loanAccountNumber || !branchCode) { showToast('Enter Loan Account Number and Branch Code', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/approve?loanAccountNumber=' + loanAccountNumber + '&branchCode=' + branchCode, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan Against FD approved!', 'success');
            const d = data.data;
            document.getElementById('approveResult').innerHTML = `
                <table style="margin-top:12px;">
                    <tr><th>Loan Account</th><td>${d.loanAccountNumber}</td><th>Name</th><td>${d.accountName}</td></tr>
                    <tr><th>FD Account</th><td>${d.fdAccountNumber}</td><th>Loan Amount</th><td>${d.loanAmount}</td></tr>
                    <tr><th>FD Deposit Amt</th><td>${d.fdDepositAmount}</td><th>Interest Rate</th><td>${d.intRate}%</td></tr>
                    <tr><th>Tenure</th><td>${d.loanTenureMonths} months</td><th>Status</th><td>${d.loanStatus}</td></tr>
                    <tr><th>Purpose</th><td>${d.loanPurpose}</td><th>Repayment Mode</th><td>${d.repaymentMode}</td></tr>
                </table>`;
        } else {
            showToast(data.message || data.errorMessage || 'Approval failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// F2 Customer Lookup
document.addEventListener('keydown', function(e) {
    if (e.key === 'F2') { e.preventDefault(); openCustomerLookup(); }
    if (e.key === 'Escape') closeCustomerLookup();
});

async function openCustomerLookup() {
    const branchCode = document.getElementById('branchCode').value;
    if (!branchCode) { showToast('Enter Branch Code first', 'error'); return; }

    const modal = document.getElementById('customerModal');
    modal.classList.add('show');
    document.getElementById('customerListContent').innerHTML = 'Loading...';

    try {
        const res = await authFetch(CTX + '/api/v1/customer/approvedCustomers?branchCode=' + branchCode);
        const data = await res.json();
        if (res.ok && data.data && data.data.length > 0) {
            let html = `<table>
                <thead><tr><th>Customer ID</th><th>Name</th><th>PAN</th><th>Status</th></tr></thead>
                <tbody>`;
            data.data.forEach(c => {
                html += `<tr onclick="selectCustomer(${c.customerId}, '${c.memberFName} ${c.memberMName || ''} ${c.memberLName}')">
                    <td>${c.customerId}</td>
                    <td>${c.memberFName} ${c.memberMName || ''} ${c.memberLName}</td>
                    <td>${c.pan || ''}</td>
                    <td>${c.customerStatus}</td>
                </tr>`;
            });
            html += '</tbody></table>';
            document.getElementById('customerListContent').innerHTML = html;
        } else {
            document.getElementById('customerListContent').innerHTML = '<p style="text-align:center;color:#718096;padding:20px;">No approved customers found for this branch</p>';
        }
    } catch (e) {
        document.getElementById('customerListContent').innerHTML = '<p style="color:#e53e3e;padding:20px;">Error loading customers</p>';
    }
}

function selectCustomer(customerId, customerName) {
    document.getElementById('customerId').value = customerId;
    document.getElementById('accountName').value = customerName.trim();
    closeCustomerLookup();
}

function closeCustomerLookup() {
    document.getElementById('customerModal').classList.remove('show');
}
