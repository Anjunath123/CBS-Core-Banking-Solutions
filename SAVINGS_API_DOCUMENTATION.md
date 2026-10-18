# Core Banking Service - Savings Account REST API Documentation

Base URL: `/api/v1/savings`

---

## 1. Create Savings Account

Creates a new savings/current account for a customer. Account is created in Pending state with zero balance.

**Endpoint:** `POST /api/v1/savings/create`

### Request

```json
{
  "branchCode": 1,
  "accountName": "John Doe",
  "product": "SB",
  "scheme": "REGULAR",
  "currencyCode": "INR",
  "openDate": "2025-01-01T00:00:00.000+00:00",
  "customerId": 100001,
  "minimumBalance": 1000.0
}
```

| Field          | Type    | Required | Validation                        | Description                                  |
|----------------|---------|----------|-----------------------------------|----------------------------------------------|
| branchCode     | Integer | Yes      | @NotNull                          | Branch code where account is opened          |
| accountName    | String  | Yes      | @NotBlank                         | Name of the account holder                   |
| product        | String  | Yes      | @NotBlank                         | Product code (e.g. SB, CA)                   |
| scheme         | String  | Yes      | @NotBlank                         | Scheme code (e.g. REGULAR, PREMIUM)          |
| currencyCode   | String  | Yes      | @NotBlank                         | Currency code (e.g. INR, USD)                |
| openDate       | Date    | Yes      | @NotNull                          | Account opening date                         |
| customerId     | Long    | Yes      | @NotNull                          | Customer ID                                  |
| minimumBalance | Double  | No       | Defaults to 0.0 if not provided   | Minimum balance to be maintained in account  |

### Response (201 Created)

```json
{
  "data": {
    "branchCode": 1,
    "accountNumber": "SB10100000001",
    "accountName": "John Doe",
    "product": "SB",
    "scheme": "REGULAR",
    "availableBalance": 0.0,
    "currentBalance": 0.0,
    "lienAmount": 0.0,
    "minimumBalance": 1000.0,
    "currencyCode": "INR",
    "openDate": "2025-01-01T00:00:00.000+00:00",
    "customerId": 100001,
    "isActive": 1,
    "authStatus": "P",
    "accountStatus": 1
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Savings account created successfully",
  "successCode": "201 CREATED"
}
```

| Field            | Type    | Description                                      |
|------------------|---------|--------------------------------------------------|
| branchCode       | Integer | Branch code                                      |
| accountNumber    | String  | Auto-generated account number (prefix: SB)       |
| accountName      | String  | Account holder name                              |
| product          | String  | Product code                                     |
| scheme           | String  | Scheme code                                      |
| availableBalance | Double  | Available balance (initialized to 0.0)           |
| currentBalance   | Double  | Current balance (initialized to 0.0)             |
| lienAmount       | Double  | Lien/hold amount (initialized to 0.0)            |
| minimumBalance   | Double  | Minimum balance requirement                      |
| currencyCode     | String  | Currency code                                    |
| openDate         | Date    | Account opening date                             |
| customerId       | Long    | Customer ID                                      |
| isActive         | Integer | 1 = Active, 0 = Inactive                        |
| authStatus       | String  | P = Pending, A = Approved, R = Rejected          |
| accountStatus    | Integer | 1 = Pending, 3 = Active/Live                    |

### Validation Error Response (400 Bad Request)

```json
{
  "timestamp": "2025-01-15T10:30:00",
  "status": 400,
  "message": "Branch code is required",
  "path": "uri=/api/v1/savings/create"
}
```

---

## 2. Approve Savings Account

Approves a pending savings account. Only accounts in Pending (P) auth status can be approved.

**Endpoint:** `PUT /api/v1/savings/approve`

### Request Parameters

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|
| accountNumber | String  | Yes      | Savings account number         |
| branchCode    | Integer | Yes      | Branch code of the account     |

### Example Request

```
PUT /api/v1/savings/approve?accountNumber=SB10100000001&branchCode=1
```

### Response (200 OK)

```json
{
  "data": {
    "branchCode": 1,
    "accountNumber": "SB10100000001",
    "accountName": "John Doe",
    "product": "SB",
    "scheme": "REGULAR",
    "availableBalance": 0.0,
    "currentBalance": 0.0,
    "lienAmount": 0.0,
    "minimumBalance": 1000.0,
    "currencyCode": "INR",
    "openDate": "2025-01-01T00:00:00.000+00:00",
    "customerId": 100001,
    "isActive": 1,
    "authStatus": "A",
    "accountStatus": 3
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Savings account approved successfully",
  "successCode": "200 OK"
}
```

### Error Responses

| Status | Condition                              | Message                                                        |
|--------|----------------------------------------|----------------------------------------------------------------|
| 400    | Account not found                      | Savings account not found: {accountNumber}                     |
| 400    | Account not in pending state           | Account is not in pending state. Current status: {authStatus}  |

---

## 3. Credit Amount (Deposit Money)

Credits (deposits) money into an active and approved savings account. Both `availableBalance` and `currentBalance` are increased.

**Endpoint:** `POST /api/v1/savings/credit`

### Request

```json
{
  "branchCode": 1,
  "accountNumber": "SB10100000001",
  "amount": 500000.0,
  "narration": "Cash deposit"
}
```

| Field         | Type    | Required | Validation                                  | Description                          |
|---------------|---------|----------|---------------------------------------------|--------------------------------------|
| branchCode    | Integer | Yes      | @NotNull                                    | Branch code of the account           |
| accountNumber | String  | Yes      | @NotBlank                                   | Savings account number               |
| amount        | Double  | Yes      | @NotNull, @DecimalMin("0.01")               | Amount to credit (must be > 0)       |
| narration     | String  | No       | Defaults to empty string                    | Transaction narration/description    |

### Response (200 OK)

```json
{
  "data": {
    "branchCode": 1,
    "accountNumber": "SB10100000001",
    "accountName": "John Doe",
    "product": "SB",
    "scheme": "REGULAR",
    "availableBalance": 500000.0,
    "currentBalance": 500000.0,
    "lienAmount": 0.0,
    "minimumBalance": 1000.0,
    "currencyCode": "INR",
    "openDate": "2025-01-01T00:00:00.000+00:00",
    "customerId": 100001,
    "isActive": 1,
    "authStatus": "A",
    "accountStatus": 3
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Amount credited successfully",
  "successCode": "200 OK"
}
```

### Error Responses

| Status | Condition                                    | Message                                                              |
|--------|----------------------------------------------|----------------------------------------------------------------------|
| 400    | Account not found or not active/approved     | Savings account {accountNumber} not found or not active/approved.    |
| 400    | Amount is null or <= 0                       | Amount must be greater than 0                                        |

---

## 4. Get Account Balance / Details

Fetches the account details and balance information for a savings account.

**Endpoint:** `GET /api/v1/savings/balance`

### Request Parameters

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|
| accountNumber | String  | Yes      | Savings account number         |
| branchCode    | Integer | Yes      | Branch code of the account     |

### Example Request

```
GET /api/v1/savings/balance?accountNumber=SB10100000001&branchCode=1
```

### Response (200 OK)

```json
{
  "data": {
    "branchCode": 1,
    "accountNumber": "SB10100000001",
    "accountName": "John Doe",
    "product": "SB",
    "scheme": "REGULAR",
    "availableBalance": 500000.0,
    "currentBalance": 500000.0,
    "lienAmount": 0.0,
    "minimumBalance": 1000.0,
    "currencyCode": "INR",
    "openDate": "2025-01-01T00:00:00.000+00:00",
    "customerId": 100001,
    "isActive": 1,
    "authStatus": "A",
    "accountStatus": 3
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Balance fetched successfully",
  "successCode": "200 OK"
}
```

### Balance Fields Explained

| Field            | Description                                                                 |
|------------------|-----------------------------------------------------------------------------|
| availableBalance | Total available balance (reduced when TD is approved / debit happens)       |
| currentBalance   | Current ledger balance                                                      |
| lienAmount       | Amount held/blocked (cannot be used for TD or withdrawal)                   |
| minimumBalance   | Minimum balance that must be maintained (protected from TD debit)           |

**Withdrawable Balance Formula (used during TD approval):**
```
withdrawable = availableBalance - lienAmount - minimumBalance
```

### Error Responses

| Status | Condition          | Message                                          |
|--------|--------------------|--------------------------------------------------|
| 400    | Account not found  | Savings account not found: {accountNumber}       |

---

## Common Error Response Format

All error responses follow this structure:

```json
{
  "timestamp": "2025-01-15T10:30:00",
  "status": 400,
  "message": "Error description",
  "path": "uri=/api/v1/savings/credit"
}
```

---

## Account Status Codes

| Code | Status      |
|------|-------------|
| 1    | Pending     |
| 3    | Active/Live |

## Auth Status Codes

| Code | Status   |
|------|----------|
| P    | Pending  |
| A    | Approved |
| R    | Rejected |

---

## Account Number Generation

- Savings account numbers are auto-generated with prefix `SB`
- Format: `SB` + `{tenantId}` + `{branchCode (2 digits)}` + `{sequence (6 digits)}`
- Example: `SB10100000001` (tenant=1, branch=01, sequence=000001)

---

## Integration with TD (Fixed Deposit) Approval

When a Term Deposit (FD) is approved via `PUT /api/v1/deposit/approveTDAccount`, the system:

1. Looks up the savings account specified in `debitAccID` field of the deposit
2. Validates the account is active and approved
3. Checks: `availableBalance - lienAmount - minimumBalance >= depositAmount`
4. Debits the savings account (`availableBalance` and `currentBalance` reduced)
5. Approves the TD account
6. Generates DR/CR voucher entries in `D009040` table

If any step fails, the entire transaction rolls back — no partial updates.

### Example: TD Approval Balance Impact

| Step                        | SB Available Balance |
|-----------------------------|---------------------|
| After credit of ₹500,000   | 500,000.0           |
| After TD approval of ₹100,000 | 400,000.0        |
| After TD approval of ₹200,000 | 200,000.0        |
| TD approval of ₹250,000    | **REJECTED** (withdrawable = 200,000 - 0 - 1,000 = 199,000 < 250,000) |
