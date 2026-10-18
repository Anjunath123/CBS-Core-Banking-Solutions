const API_BASE = CTX + '/api/v1/savings';

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

function renderAccountTable(d) {
    return `<table style="margin-top:12px;">
        <tr><th>Account No</th><td>${d.accountNumber}</td><th>Name</th><td>${d.accountName}</td></tr>
        <tr><th>Available Balance</th><td>${d.availableBalance}</td><th>Current Balance</th><td>${d.currentBalance}</td></tr>
        <tr><th>Auth Status</th><td>${d.authStatus}</td><th>Account Status</th><td>${d.accountStatus}</td></tr>
        <tr><th>Product</th><td>${d.product}</td><th>Scheme</th><td>${d.scheme}</td></tr>
    </table>`;
}

async function createSavings() {
    const form = document.getElementById('savingsForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const openDateVal = document.getElementById('openDate').value;
    const payload = {
        branchCode: parseInt(document.getElementById('branchCode').value),
        customerId: parseInt(document.getElementById('customerId').value),
        accountName: document.getElementById('accountName').value,
        product: document.getElementById('product').value,
        scheme: document.getElementById('scheme').value,
        currencyCode: document.getElementById('currencyCode').value,
        openDate: openDateVal ? new Date(openDateVal).toISOString() : null,
        minimumBalance: parseFloat(document.getElementById('minimumBalance').value) || 0
    };

    try {
        const res = await authFetch(API_BASE + '/create', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Savings Account created! Account: ' + data.data.accountNumber, 'success');
            form.reset();
        } else {
            showToast(data.message || data.errorMessage || 'Failed to create savings account', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function approveSavings() {
    const accountNumber = document.getElementById('approveAccNo').value;
    const branchCode = document.getElementById('approveBranch').value;
    if (!accountNumber || !branchCode) { showToast('Enter Account Number and Branch Code', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/approve?accountNumber=' + accountNumber + '&branchCode=' + branchCode, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) {
            showToast('Savings Account approved!', 'success');
            document.getElementById('approveResult').innerHTML = renderAccountTable(data.data);
        } else {
            showToast(data.message || data.errorMessage || 'Approval failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function creditAmount() {
    const branchCode = document.getElementById('creditBranch').value;
    const accountNumber = document.getElementById('creditAccNo').value;
    const amount = document.getElementById('creditAmount').value;
    if (!branchCode || !accountNumber || !amount) { showToast('Fill all required fields', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/credit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                branchCode: parseInt(branchCode), accountNumber, amount: parseFloat(amount),
                narration: document.getElementById('creditNarration').value || ''
            })
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Amount credited successfully!', 'success');
            document.getElementById('creditResult').innerHTML = renderAccountTable(data.data);
        } else {
            showToast(data.message || data.errorMessage || 'Credit failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function checkBalance() {
    const accountNumber = document.getElementById('balAccNo').value;
    const branchCode = document.getElementById('balBranch').value;
    if (!accountNumber || !branchCode) { showToast('Enter Account Number and Branch Code', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/balance?accountNumber=' + accountNumber + '&branchCode=' + branchCode);
        const data = await res.json();
        if (res.ok) {
            document.getElementById('balanceResult').innerHTML = renderAccountTable(data.data);
        } else {
            showToast(data.message || data.errorMessage || 'Account not found', 'error');
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
