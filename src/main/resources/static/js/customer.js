const API_BASE = CTX + '/api/v1/customer';

// Tab switching
document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        tab.classList.add('active');
        document.getElementById('tab-' + tab.dataset.tab).classList.add('active');
    });
});

// Toast notification
function showToast(msg, type) {
    const toast = document.getElementById('toast');
    toast.textContent = msg;
    toast.className = 'toast toast-' + type;
    toast.style.display = 'block';
    setTimeout(() => toast.style.display = 'none', 4000);
}

// Format date from yyyy-MM-dd to dd-MM-yyyy
function formatDate(dateStr) {
    if (!dateStr) return '';
    const [y, m, d] = dateStr.split('-');
    return d + '-' + m + '-' + y;
}

// Dynamic address rows
function addAddressRow() {
    const row = document.createElement('tr');
    row.innerHTML = `
        <td><select><option value="Permanent">Permanent</option><option value="Correspondence">Correspondence</option><option value="Office">Office</option></select></td>
        <td><input type="text" placeholder="Address Line 1"></td>
        <td><input type="text" placeholder="Address Line 2"></td>
        <td><input type="text" placeholder="City Code"></td>
        <td><input type="text" placeholder="State Code"></td>
        <td><input type="text" placeholder="Country Code"></td>
        <td><input type="text" placeholder="PIN Code"></td>
        <td><button class="btn btn-danger btn-sm" onclick="this.closest('tr').remove()">X</button></td>`;
    document.getElementById('addressBody').appendChild(row);
}

// Dynamic KYC rows
function addKycRow() {
    const row = document.createElement('tr');
    row.innerHTML = `
        <td><select><option value="PAN">PAN</option><option value="Aadhaar">Aadhaar</option><option value="Passport">Passport</option><option value="VoterID">Voter ID</option></select></td>
        <td><input type="text" placeholder="Document ID"></td>
        <td><input type="text" placeholder="Name as in document"></td>
        <td><input type="date"></td>
        <td><input type="date"></td>
        <td><input type="text" placeholder="Remarks"></td>
        <td><button class="btn btn-danger btn-sm" onclick="this.closest('tr').remove()">X</button></td>`;
    document.getElementById('kycBody').appendChild(row);
}

// Dynamic bank rows
function addBankRow() {
    const rows = document.getElementById('bankBody').rows.length;
    if (rows >= 3) { showToast('Maximum 3 bank accounts allowed', 'error'); return; }
    const row = document.createElement('tr');
    row.innerHTML = `
        <td><input type="text" placeholder="Account Holder Name"></td>
        <td><input type="text" placeholder="Account Number"></td>
        <td><input type="text" placeholder="IFSC Code"></td>
        <td><input type="text" placeholder="Bank Name"></td>
        <td><input type="text" placeholder="Branch Name"></td>
        <td><select><option value="NEFT">NEFT</option><option value="RTGS">RTGS</option><option value="IMPS">IMPS</option></select></td>
        <td><select><option value="1">Yes</option><option value="0">No</option></select></td>
        <td><button class="btn btn-danger btn-sm" onclick="this.closest('tr').remove()">X</button></td>`;
    document.getElementById('bankBody').appendChild(row);
}

// Collect table data
function getAddresses() {
    return Array.from(document.getElementById('addressBody').rows).map(r => {
        const cells = r.querySelectorAll('input, select');
        return {
            addressType: cells[0].value, address1: cells[1].value, address2: cells[2].value,
            cityCode: cells[3].value, stateCode: cells[4].value, countryCode: cells[5].value, pinCode: cells[6].value
        };
    });
}

function getKycDocuments() {
    return Array.from(document.getElementById('kycBody').rows).map((r, i) => {
        const cells = r.querySelectorAll('input, select');
        return {
            srNo: i + 1, docType: cells[0].value, docIdNum: cells[1].value, nameAsInDocument: cells[2].value,
            issuedDate: formatDate(cells[3].value), expiryDate: formatDate(cells[4].value), remarks: cells[5].value
        };
    });
}

function getBankInfo() {
    return Array.from(document.getElementById('bankBody').rows).map((r, i) => {
        const cells = r.querySelectorAll('input, select');
        return {
            srNo: i + 1, accountHolderName: cells[0].value, accountNumber: cells[1].value, ifscCode: cells[2].value,
            bankName: cells[3].value, branchName: cells[4].value, paymentMode: cells[5].value, primaryAccount: parseInt(cells[6].value)
        };
    });
}

// Validate child data sections
function validateChildData() {
    const addrRows = document.getElementById('addressBody').rows;
    if (addrRows.length === 0) { showToast('Please add at least one Address', 'error'); return false; }
    for (let i = 0; i < addrRows.length; i++) {
        const cells = addrRows[i].querySelectorAll('input, select');
        if (!cells[1].value.trim()) { showToast('Address Line 1 is required in Address row ' + (i + 1), 'error'); cells[1].focus(); return false; }
        if (!cells[3].value.trim()) { showToast('City Code is required in Address row ' + (i + 1), 'error'); cells[3].focus(); return false; }
        if (!cells[4].value.trim()) { showToast('State Code is required in Address row ' + (i + 1), 'error'); cells[4].focus(); return false; }
        if (!cells[6].value.trim()) { showToast('PIN Code is required in Address row ' + (i + 1), 'error'); cells[6].focus(); return false; }
    }

    const kycRows = document.getElementById('kycBody').rows;
    if (kycRows.length === 0) { showToast('Please add at least one KYC Document', 'error'); return false; }
    for (let i = 0; i < kycRows.length; i++) {
        const cells = kycRows[i].querySelectorAll('input, select');
        if (!cells[1].value.trim()) { showToast('Document ID is required in KYC row ' + (i + 1), 'error'); cells[1].focus(); return false; }
        if (!cells[2].value.trim()) { showToast('Name as in Document is required in KYC row ' + (i + 1), 'error'); cells[2].focus(); return false; }
        if (!cells[3].value) { showToast('Issued Date is required in KYC row ' + (i + 1), 'error'); cells[3].focus(); return false; }
        if (!cells[4].value) { showToast('Expiry Date is required in KYC row ' + (i + 1), 'error'); cells[4].focus(); return false; }
    }

    const bankRows = document.getElementById('bankBody').rows;
    if (bankRows.length === 0) { showToast('Please add at least one Bank Account', 'error'); return false; }
    for (let i = 0; i < bankRows.length; i++) {
        const cells = bankRows[i].querySelectorAll('input, select');
        if (!cells[0].value.trim()) { showToast('Account Holder Name is required in Bank row ' + (i + 1), 'error'); cells[0].focus(); return false; }
        if (!cells[1].value.trim()) { showToast('Account Number is required in Bank row ' + (i + 1), 'error'); cells[1].focus(); return false; }
        if (!cells[2].value.trim()) { showToast('IFSC Code is required in Bank row ' + (i + 1), 'error'); cells[2].focus(); return false; }
        if (!cells[3].value.trim()) { showToast('Bank Name is required in Bank row ' + (i + 1), 'error'); cells[3].focus(); return false; }
    }
    return true;
}

// Submit customer
async function submitCustomer() {
    const form = document.getElementById('customerForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }
    if (!validateChildData()) return;

    const payload = {
        branchCode: parseInt(document.getElementById('branchCode').value),
        memberFName: document.getElementById('memberFName').value,
        memberMName: document.getElementById('memberMName').value || '',
        memberLName: document.getElementById('memberLName').value,
        pan: document.getElementById('pan').value.toUpperCase(),
        dateOfBirth: formatDate(document.getElementById('dateOfBirth').value),
        gender: document.getElementById('gender').value,
        residentStatus: document.getElementById('residentStatus').value,
        custCategory: document.getElementById('custCategory').value,
        customerType: document.getElementById('customerType').value,
        addresses: getAddresses(),
        kycDocumentDtoList: getKycDocuments(),
        customerBankInfoDtoList: getBankInfo()
    };

    try {
        const res = await authFetch(API_BASE + '/saveCustomer', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Customer created! ID: ' + data.data.customerId, 'success');
            resetForm();
        } else {
            showToast(data.message || data.errorMessage || 'Failed to create customer', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Search customer
async function searchCustomer() {
    const customerId = document.getElementById('searchCustomerId').value;
    const branchCode = document.getElementById('searchBranchCode').value;
    if (!customerId || !branchCode) { showToast('Enter Customer ID and Branch Code', 'error'); return; }

    try {
        const res = await authFetch(API_BASE + '/getCustomer?customerId=' + customerId + '&branchCode=' + branchCode);
        const data = await res.json();
        if (res.ok && data.data) {
            renderCustomerDetails(data.data);
            document.getElementById('searchResult').style.display = 'block';
        } else {
            showToast(data.message || data.errorMessage || 'Customer not found', 'error');
            document.getElementById('searchResult').style.display = 'none';
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

function renderCustomerDetails(c) {
    let html = `
        <table>
            <tr><th>Customer ID</th><td>${c.customerId}</td><th>Branch</th><td>${c.branchCode}</td></tr>
            <tr><th>Name</th><td colspan="3">${c.memberFName} ${c.memberMName || ''} ${c.memberLName}</td><tr><th>PAN</th><td>${c.pan}</td></tr>
            </td><th>Gender</th><td>${c.gender}</td></tr>
            <tr><th>DOB</th><td>${c.dateOfBirthStr || c.dateOfBirth || ''}</td><th>Status</th><td>${c.customerStatus}</td></tr>
            <th>Category</th><td>${c.custCategory}</td></tr>
        </table>`;

    // Address Details
    if (c.addresses && c.addresses.length > 0) {
        html += `<h4 style="margin:16px 0 8px;color:#2c5282;">Address Details</h4>
        <table>
            <thead><tr><th>Sr#</th><th>Type</th><th>Address 1</th><th>Address 2</th><th>City</th><th>State</th><th>Country</th><th>PIN</th><th>Status</th></tr></thead>
            <tbody>${c.addresses.map((a, i) => `<tr>
                <td>${a.srNo || i + 1}</td><td>${a.addressType || ''}</td><td>${a.address1 || ''}</td><td>${a.address2 || ''}</td>
                <td>${a.cityCode || ''}</td><td>${a.stateCode || ''}</td><td>${a.countryCode || ''}</td><td>${a.pinCode || ''}</td><td>${a.status || ''}</td>
            </tr>`).join('')}</tbody>
        </table>`;
    }

    // KYC Documents
    if (c.kycDocumentDtoList && c.kycDocumentDtoList.length > 0) {
        html += `<h4 style="margin:16px 0 8px;color:#2c5282;">KYC Documents</h4>
        <table>
            <thead><tr><th>Sr#</th><th>Doc Type</th><th>Doc ID</th><th>Name as in Doc</th><th>Issued Date</th><th>Expiry Date</th><th>Remarks</th><th>Status</th></tr></thead>
            <tbody>${c.kycDocumentDtoList.map((k, i) => `<tr>
                <td>${k.srNo || i + 1}</td><td>${k.docType || ''}</td><td>${k.docIdNum || ''}</td><td>${k.nameAsInDocument || ''}</td>
                <td>${k.issuedDate || ''}</td><td>${k.expiryDate || ''}</td><td>${k.remarks || ''}</td><td>${k.kycStatus || ''}</td>
            </tr>`).join('')}</tbody>
        </table>`;
    }

    // Bank Payment Details
    if (c.customerBankInfoDtoList && c.customerBankInfoDtoList.length > 0) {
        html += `<h4 style="margin:16px 0 8px;color:#2c5282;">Bank Payment Details</h4>
        <table>
            <thead><tr><th>Sr#</th><th>Account Holder</th><th>Account No</th><th>IFSC</th><th>Bank</th><th>Branch</th><th>Payment Mode</th><th>Primary</th></tr></thead>
            <tbody>${c.customerBankInfoDtoList.map((b, i) => `<tr>
                <td>${b.srNo || i + 1}</td><td>${b.accountHolderName || ''}</td><td>${b.accountNumber || ''}</td><td>${b.ifscCode || ''}</td>
                <td>${b.bankName || ''}</td><td>${b.branchName || ''}</td><td>${b.paymentMode || ''}</td><td>${b.primaryAccount === 1 ? 'Yes' : 'No'}</td>
            </tr>`).join('')}</tbody>
        </table>`;
    }

    document.getElementById('customerDetails').innerHTML = html;
    window._currentCustomer = c;
}

// Approve
async function approveCustomer() {
    const c = window._currentCustomer;
    if (!c) return;
    try {
        const res = await authFetch(API_BASE + '/approveCustomer?customerId=' + c.customerId + '&branchCode=' + c.branchCode, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) { showToast('Customer approved!', 'success'); searchCustomer(); }
        else showToast(data.message || data.errorMessage || 'Approval failed', 'error');
    } catch (e) { showToast('Error: ' + e.message, 'error'); }
}

// Reject
async function rejectCustomer() {
    const c = window._currentCustomer;
    if (!c) return;
    try {
        const res = await authFetch(API_BASE + '/rejectCustomer?customerId=' + c.customerId + '&branchCode=' + c.branchCode, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) { showToast('Customer rejected', 'success'); searchCustomer(); }
        else showToast(data.message || data.errorMessage || 'Rejection failed', 'error');
    } catch (e) { showToast('Error: ' + e.message, 'error'); }
}

// Delete
async function deleteCustomer() {
    const c = window._currentCustomer;
    if (!c) return;
    if (!confirm('Are you sure you want to delete this customer?')) return;
    try {
        const res = await authFetch(API_BASE + '/deleteCustomer?customerId=' + c.customerId + '&branchCode=' + c.branchCode, { method: 'DELETE' });
        if (res.ok) {
            showToast('Customer deleted', 'success');
            document.getElementById('searchResult').style.display = 'none';
        } else {
            const data = await res.json();
            showToast(data.message || data.errorMessage || 'Delete failed', 'error');
        }
    } catch (e) { showToast('Error: ' + e.message, 'error'); }
}

function resetForm() {
    document.getElementById('customerForm').reset();
    document.getElementById('addressBody').innerHTML = '';
    document.getElementById('kycBody').innerHTML = '';
    document.getElementById('bankBody').innerHTML = '';
}

// Initialize with one row each
//addAddressRow();
//addKycRow();
//addBankRow();
