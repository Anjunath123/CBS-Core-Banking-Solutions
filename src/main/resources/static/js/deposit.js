const API_BASE = CTX + '/api/v1/deposit';

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

async function createDeposit() {
    const form = document.getElementById('depositForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const openDateVal = document.getElementById('openDate').value;
    const payload = {
        branchCode: parseInt(document.getElementById('branchCode').value),
        customerId: parseInt(document.getElementById('customerId').value),
        accountName: document.getElementById('accountName').value,
        product: document.getElementById('product').value,
        scheme: document.getElementById('scheme').value,
        modeOfOprn: document.getElementById('modeOfOprn').value,
        depositAmount: parseFloat(document.getElementById('depositAmount').value),
        intRate: parseFloat(document.getElementById('intRate').value),
        currencyCode: document.getElementById('currencyCode').value,
        depositMonths: parseInt(document.getElementById('depositMonths').value),
        depositDays: parseInt(document.getElementById('depositDays').value) || 0,
        openDate: openDateVal ? new Date(openDateVal).toISOString() : null,
        intPayFreq: document.getElementById('intPayFreq').value,
        debitAccID: document.getElementById('debitAccID').value,
        depSource: document.getElementById('depSource').value,
        lockPeriod: parseInt(document.getElementById('lockPeriod').value) || 0,
        remarks: document.getElementById('remarks').value || ''
    };

    try {
        const res = await authFetch(API_BASE + '/createFDAccount', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('FD Account created! Account: ' + data.data.accountNumber, 'success');
            form.reset();
        } else {
            showToast(data.message || data.errorMessage || 'Failed to create FD account', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function approveDeposit() {
    const accountNumber = document.getElementById('approveAccountNumber').value;
    const branchCode = document.getElementById('approveBranchCode').value;
    if (!accountNumber || !branchCode) { showToast('Enter Account Number and Branch Code', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/approveTDAccount', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ accountNumber, branchCode: parseInt(branchCode) })
        });
        const data = await res.json();
        if (res.ok) {
            showToast('FD Account approved!', 'success');
            const d = data.data;
            document.getElementById('approveResult').innerHTML = `
                <table style="margin-top:12px;">
                    <tr><th>Account No</th><td>${d.accountNumber}</td><th>Name</th><td>${d.accountName}</td></tr>
                    <tr><th>Deposit Amt</th><td>${d.depositAmount}</td><th>Interest Rate</th><td>${d.intRate}%</td></tr>
                    <tr><th>Status</th><td>${d.depositStatus}</td><th>Product</th><td>${d.product}</td></tr>
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
