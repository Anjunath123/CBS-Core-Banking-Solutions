const HL_API = CTX + '/api/v1/home-loan';

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

function formatCurrency(amount) {
    if (amount == null) return '₹0';
    return '₹' + Number(amount).toLocaleString('en-IN', { maximumFractionDigits: 2 });
}

function statusBadge(status) {
    return `<span class="status-badge status-${status}">${status}</span>`;
}

function renderLoanSummary(d) {
    return `
        <div class="loan-summary">
            <div class="summary-item"><label>Loan Account</label><div class="value" style="font-size:14px;">${d.loanAccountNumber}</div></div>
            <div class="summary-item"><label>Status</label><div class="value">${statusBadge(d.loanStatus)}</div></div>
            <div class="summary-item"><label>Loan Amount</label><div class="value">${formatCurrency(d.loanAmount)}</div></div>
            <div class="summary-item"><label>EMI</label><div class="value">${formatCurrency(d.emiAmount)}</div></div>
            <div class="summary-item"><label>Interest Rate</label><div class="value">${d.interestRate}%</div></div>
            <div class="summary-item"><label>Tenure</label><div class="value">${d.tenureMonths} months</div></div>
            <div class="summary-item"><label>Outstanding</label><div class="value">${formatCurrency(d.outstandingPrincipal)}</div></div>
            <div class="summary-item"><label>CIBIL Score</label><div class="value">${d.cibilScore || 'N/A'}</div></div>
            <div class="summary-item"><label>EMIs Paid</label><div class="value">${d.emisPaid || 0} / ${d.tenureMonths}</div></div>
            <div class="summary-item"><label>Penalty</label><div class="value">${formatCurrency(d.penaltyAmount)}</div></div>
        </div>
        <table>
            <tr><th>Applicant</th><td>${d.applicantName || ''}</td><th>Customer ID</th><td>${d.customerId}</td></tr>
            <tr><th>Property</th><td>${d.propertyAddress || ''}</td><th>Disbursement Type</th><td>${d.disbursementType || ''}</td></tr>
            <tr><th>Beneficiary</th><td>${d.beneficiaryName || 'N/A'}</td><th>Beneficiary A/C</th><td>${d.beneficiaryAccountNumber || 'N/A'}</td></tr>
            <tr><th>Applied</th><td>${formatDate(d.applicationDate)}</td><th>Approved</th><td>${formatDate(d.approvalDate)}</td></tr>
            <tr><th>Disbursed</th><td>${formatDate(d.disbursementDate)}</td><th>Total Interest Paid</th><td>${formatCurrency(d.totalInterestPaid)}</td></tr>
        </table>`;
}

function formatDate(d) {
    if (!d) return 'N/A';
    return new Date(d).toLocaleDateString('en-IN');
}

// STEP 1: Apply
async function applyForLoan() {
    const form = document.getElementById('applyForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }

    const payload = {
        branchCode: parseInt(document.getElementById('applyBranchCode').value),
        customerId: parseInt(document.getElementById('applyCustomerId').value),
        applicantName: document.getElementById('applyApplicantName').value,
        loanAmount: parseFloat(document.getElementById('applyLoanAmount').value),
        interestRate: parseFloat(document.getElementById('applyInterestRate').value),
        tenureMonths: parseInt(document.getElementById('applyTenureMonths').value),
        monthlyIncome: parseFloat(document.getElementById('applyMonthlyIncome').value),
        existingEmiTotal: parseFloat(document.getElementById('applyExistingEmi').value) || 0,
        propertyAddress: document.getElementById('applyPropertyAddress').value,
        propertyValuation: parseFloat(document.getElementById('applyPropertyValuation').value) || 0,
        disbursementType: document.getElementById('applyDisbursementType').value
    };

    try {
        const res = await authFetch(HL_API + '/apply', {
            method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan Application Submitted! Account: ' + data.data.loanAccountNumber, 'success');
            document.getElementById('applyResult').innerHTML = renderLoanSummary(data.data);
            form.reset();
        } else {
            showToast(data.errorMessage || 'Application failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 2: Verify Eligibility
async function verifyEligibility() {
    const accNo = document.getElementById('verifyLoanAccNo').value;
    if (!accNo) { showToast('Enter Loan Account Number', 'error'); return; }

    try {
        const res = await authFetch(HL_API + '/verify/' + accNo, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) {
            const d = data.data;
            const eligible = d.loanStatus === 'ELIGIBLE';
            showToast(eligible ? 'Customer is ELIGIBLE!' : 'Customer is NOT ELIGIBLE', eligible ? 'success' : 'error');
            document.getElementById('verifyResult').innerHTML = `
                <div style="margin-top:16px;">
                    <div class="loan-summary">
                        <div class="summary-item"><label>CIBIL Score</label><div class="value" style="color:${d.cibilScore >= 650 ? '#38a169' : '#e53e3e'}">${d.cibilScore}</div></div>
                        <div class="summary-item"><label>Monthly Income</label><div class="value">${formatCurrency(d.monthlyIncome)}</div></div>
                        <div class="summary-item"><label>Requested Amount</label><div class="value">${formatCurrency(d.loanAmount)}</div></div>
                        <div class="summary-item"><label>Status</label><div class="value">${statusBadge(d.loanStatus)}</div></div>
                        <div class="summary-item"><label>Max EMI (40% income)</label><div class="value">${formatCurrency(d.monthlyIncome * 0.4 - (d.existingEmiTotal || 0))}</div></div>
                        <div class="summary-item"><label>Calculated EMI</label><div class="value">${formatCurrency(d.emiAmount)}</div></div>
                    </div>
                </div>`;
        } else {
            showToast(data.errorMessage || 'Verification failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 3: Approve
async function approveLoan() {
    const accNo = document.getElementById('approveLoanAccNo').value;
    if (!accNo) { showToast('Enter Loan Account Number', 'error'); return; }

    try {
        const res = await authFetch(HL_API + '/approve/' + accNo, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan Approved! EMI Schedule Generated.', 'success');
            document.getElementById('approveResult').innerHTML = renderLoanSummary(data.data);
        } else {
            showToast(data.errorMessage || 'Approval failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 4: Disburse
async function disburseLoan() {
    const loanAccountNumber = document.getElementById('disburseLoanAccNo').value;
    const beneficiaryAccountNumber = document.getElementById('disburseBeneficiaryAcc').value;
    const beneficiaryName = document.getElementById('disburseBeneficiaryName').value;
    if (!loanAccountNumber || !beneficiaryAccountNumber || !beneficiaryName) {
        showToast('Fill all disbursement fields', 'error'); return;
    }

    try {
        const res = await authFetch(HL_API + '/disburse', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ loanAccountNumber, beneficiaryAccountNumber, beneficiaryName })
        });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan Disbursed to ' + beneficiaryName, 'success');
            document.getElementById('disburseResult').innerHTML = renderLoanSummary(data.data);
        } else {
            showToast(data.errorMessage || 'Disbursement failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 5: EMI Schedule
async function loadEmiSchedule() {
    const accNo = document.getElementById('emiLoanAccNo').value;
    if (!accNo) { showToast('Enter Loan Account Number', 'error'); return; }

    try {
        const res = await authFetch(HL_API + '/emi-schedule/' + accNo);
        const data = await res.json();
        if (res.ok && data.data && data.data.length > 0) {
            let html = `<div class="emi-table"><table>
                <thead><tr><th>#</th><th>Due Date</th><th>EMI</th><th>Principal</th><th>Interest</th><th>Outstanding</th><th>Status</th><th>Penalty</th></tr></thead><tbody>`;
            data.data.forEach(e => {
                const overdue = !e.paid && new Date(e.dueDate) < new Date();
                const rowClass = e.paid ? 'emi-paid' : (overdue ? 'emi-overdue' : '');
                html += `<tr class="${rowClass}">
                    <td>${e.installmentNumber}</td>
                    <td>${formatDate(e.dueDate)}</td>
                    <td>${formatCurrency(e.emiAmount)}</td>
                    <td>${formatCurrency(e.principalComponent)}</td>
                    <td>${formatCurrency(e.interestComponent)}</td>
                    <td>${formatCurrency(e.outstandingAfterPayment)}</td>
                    <td>${e.paid ? '✅ Paid' : (overdue ? '⚠️ Overdue' : '⏳ Pending')}</td>
                    <td>${e.penaltyApplied > 0 ? formatCurrency(e.penaltyApplied) : '-'}</td>
                </tr>`;
            });
            html += '</tbody></table></div>';
            document.getElementById('emiResult').innerHTML = html;
            showToast('EMI Schedule loaded (' + data.data.length + ' installments)', 'success');
        } else {
            showToast('No EMI schedule found', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 6: Pay EMI
async function payEmi() {
    const accNo = document.getElementById('payLoanAccNo').value;
    if (!accNo) { showToast('Enter Loan Account Number', 'error'); return; }

    try {
        const res = await authFetch(HL_API + '/pay-emi/' + accNo, { method: 'POST' });
        const data = await res.json();
        if (res.ok) {
            const d = data.data;
            showToast('EMI Payment Successful! EMIs Paid: ' + d.emisPaid + '/' + d.tenureMonths, 'success');
            document.getElementById('payResult').innerHTML = renderLoanSummary(d);
        } else {
            showToast(data.errorMessage || 'EMI payment failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// STEP 9: Close Loan
async function closeLoan() {
    const accNo = document.getElementById('closeLoanAccNo').value;
    if (!accNo) { showToast('Enter Loan Account Number', 'error'); return; }

    if (!confirm('Are you sure you want to close/pre-close this loan?')) return;

    try {
        const res = await authFetch(HL_API + '/close/' + accNo, { method: 'PUT' });
        const data = await res.json();
        if (res.ok) {
            showToast('Loan Closed Successfully!', 'success');
            document.getElementById('closeResult').innerHTML = renderLoanSummary(data.data);
        } else {
            showToast(data.errorMessage || 'Closure failed', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// Search
async function searchLoan() {
    const accNo = document.getElementById('searchLoanAccNo').value;
    const custId = document.getElementById('searchCustomerId').value;

    if (!accNo && !custId) { showToast('Enter Loan Account Number or Customer ID', 'error'); return; }

    try {
        let url = accNo ? (HL_API + '/' + accNo) : (HL_API + '/customer/' + custId);
        const res = await authFetch(url);
        const data = await res.json();
        if (res.ok) {
            if (Array.isArray(data.data)) {
                if (data.data.length === 0) {
                    document.getElementById('searchResult').innerHTML = '<p style="padding:20px;color:#718096;text-align:center;">No loans found</p>';
                    return;
                }
                let html = '';
                data.data.forEach(d => { html += '<div style="margin-bottom:20px;border-bottom:2px solid #e2e8f0;padding-bottom:16px;">' + renderLoanSummary(d) + '</div>'; });
                document.getElementById('searchResult').innerHTML = html;
            } else {
                document.getElementById('searchResult').innerHTML = renderLoanSummary(data.data);
            }
            showToast('Loan(s) found', 'success');
        } else {
            showToast(data.errorMessage || 'Not found', 'error');
        }
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}
