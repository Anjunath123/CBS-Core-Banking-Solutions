# Core Banking Service - Maturity Activity REST API Documentation

Base URL: `/api/v1/activity`

---

## 1. Pre-Closure Activity

Calculates pre-closure details for a deposit account that has not yet matured.

**Endpoint:** `POST /api/v1/activity/preClosureActivity`

### Request

```json
{
  "branchCode": 1,
  "customerId": 100001,
  "accountNumber": "10100000001",
  "isDeceased": false
}
```

| Field         | Type    | Required | Description                                      |
|---------------|---------|----------|--------------------------------------------------|
| branchCode    | Integer | Yes      | Branch code of the deposit                       |
| customerId    | Long    | Yes      | Customer ID                                      |
| accountNumber | String  | Yes      | Deposit account number                           |
| isDeceased    | Boolean | No       | If true, no penalty applied (deceased case)      |

### Response (200 OK)

```json
{
  "data": {
    "accountNumber": "10100000001",
    "principal": 100000.0,
    "interestRate": 5.5,
    "interest": 2712.33,
    "maturityAmount": 102712.33,
    "closureType": "NORMAL"
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Pre-Closure data fetched successfully",
  "successCode": "200 OK"
}
```

| Field          | Type   | Description                                          |
|----------------|--------|------------------------------------------------------|
| accountNumber  | String | Deposit account number                               |
| principal      | Double | Original deposit amount                              |
| interestRate   | Double | Applicable interest rate (may be reduced for penalty) |
| interest       | Double | Calculated interest amount                           |
| maturityAmount | Double | Total payout (principal + interest)                  |
| closureType    | String | NORMAL or DECEASED                                   |

### Error Responses

| Status | Condition                          | Message                                      |
|--------|------------------------------------|----------------------------------------------|
| 400    | Account not found                  | Deposit Account Not Found                    |
| 400    | Account not live (status != 3)     | Deposit Account not Live :- {accountNumber}  |
| 400    | Already matured                    | TD already matured. Pre-closure not allowed  |

### Pre-Closure Interest Rate Rules

| Tenure         | Deceased | Interest Rate Applied              |
|----------------|----------|------------------------------------|
| < 3 months     | No       | 0% (no interest)                   |
| 3-6 months     | No       | 4%                                 |
| > 6 months     | No       | Original Rate - 2%                 |
| Any            | Yes      | Original Rate (no penalty)         |

---

## 2. Closure Activity

Closes a matured deposit account and calculates the final payout.

**Endpoint:** `POST /api/v1/activity/closure`

### Request

```json
{
  "branchCode": 1,
  "customerId": 100001,
  "accountNumber": "10100000001",
  "closureReason": "Maturity",
  "creditAccountId": "SB10100000001",
  "remarks": "Normal maturity closure"
}
```

| Field           | Type    | Required | Description                              |
|-----------------|---------|----------|------------------------------------------|
| branchCode      | Integer | Yes      | Branch code of the deposit               |
| customerId      | Long    | Yes      | Customer ID                              |
| accountNumber   | String  | Yes      | Deposit account number                   |
| closureReason   | String  | Yes      | Reason for closure                       |
| creditAccountId | String  | No       | Account to credit the payout             |
| remarks         | String  | No       | Additional remarks                       |

### Response (200 OK)

```json
{
  "data": {
    "accountNumber": "10100000001",
    "principal": 100000.0,
    "interest": 7500.0,
    "tds": 750.0,
    "totalPayout": 106750.0,
    "closureDate": "2025-01-15",
    "closureReason": "Maturity"
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Deposit closed successfully",
  "successCode": "200 OK"
}
```

| Field         | Type   | Description                              |
|---------------|--------|------------------------------------------|
| accountNumber | String | Deposit account number                   |
| principal     | Double | Original deposit amount                  |
| interest      | Double | Total interest earned                    |
| tds           | Double | TDS deducted                             |
| totalPayout   | Double | Net payout (principal + interest - tds)  |
| closureDate   | String | Date of closure (YYYY-MM-DD)             |
| closureReason | String | Reason for closure                       |

### Error Responses

| Status | Condition                          | Message                                          |
|--------|------------------------------------|--------------------------------------------------|
| 400    | Account not found                  | Deposit Account Not Found                        |
| 400    | Account not live (status != 3)     | Deposit Account not Live :- {accountNumber}      |
| 400    | Not yet matured                    | TD not yet matured. Use Pre-Closure instead.     |

---

## 3. Renewal (Full Renewal)

Renews a matured deposit with the full maturity amount (principal + interest) for a new term.

**Endpoint:** `POST /api/v1/activity/renewal`

### Request

```json
{
  "branchCode": 1,
  "customerId": 100001,
  "accountNumber": "10100000001",
  "renewalMonths": 12,
  "renewalDays": 0,
  "newIntRate": 7.5,
  "remarks": "Full renewal for 12 months"
}
```

| Field          | Type    | Required | Description                                        |
|----------------|---------|----------|----------------------------------------------------|
| branchCode     | Integer | Yes      | Branch code of the deposit                         |
| customerId     | Long    | Yes      | Customer ID                                        |
| accountNumber  | String  | Yes      | Deposit account number                             |
| renewalMonths  | Integer | No       | New tenure in months (defaults to original tenure) |
| renewalDays    | Integer | No       | New tenure days (defaults to original days)        |
| newIntRate     | Double  | No       | New interest rate (defaults to existing rate)      |
| remarks        | String  | No       | Additional remarks                                 |

### Response (200 OK)

```json
{
  "data": {
    "branchCode": 1,
    "accountNumber": "10100000001",
    "accountName": "John Doe",
    "product": "FD",
    "scheme": "REGULAR",
    "depositAmount": 107500.0,
    "depositMonths": 12,
    "depositDays": 0,
    "intRate": 7.5,
    "openDate": "2025-01-15T00:00:00.000+00:00",
    "maturityDate": "2026-01-15T00:00:00.000+00:00",
    "depositStatus": 3,
    "customerId": 100001
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Deposit renewed successfully",
  "successCode": "200 OK"
}
```

The response `data` field contains the full `DepositAccountDto` with updated values.

### Error Responses

| Status | Condition                          | Message                                      |
|--------|------------------------------------|----------------------------------------------|
| 400    | Account not found                  | Deposit Account Not Found                    |
| 400    | Account not live (status != 3)     | Deposit Account not Live :- {accountNumber}  |

---

## 4. Partial Renewal

Renews a deposit with a partial amount and pays out the remaining balance.

**Endpoint:** `POST /api/v1/activity/partialRenewal`

### Request

```json
{
  "branchCode": 1,
  "customerId": 100001,
  "accountNumber": "10100000001",
  "renewalAmount": 75000.0,
  "renewalMonths": 12,
  "renewalDays": 0,
  "newIntRate": 7.5,
  "remarks": "Partial renewal - withdraw 32500"
}
```

| Field          | Type    | Required | Description                                        |
|----------------|---------|----------|----------------------------------------------------|
| branchCode     | Integer | Yes      | Branch code of the deposit                         |
| customerId     | Long    | Yes      | Customer ID                                        |
| accountNumber  | String  | Yes      | Deposit account number                             |
| renewalAmount  | Double  | Yes      | Amount to renew (must be > 0 and <= maturity amt)  |
| renewalMonths  | Integer | No       | New tenure in months (defaults to original tenure) |
| renewalDays    | Integer | No       | New tenure days (defaults to original days)        |
| newIntRate     | Double  | No       | New interest rate (defaults to existing rate)      |
| remarks        | String  | No       | Additional remarks                                 |

### Response (200 OK)

```json
{
  "data": {
    "renewedAccount": {
      "branchCode": 1,
      "accountNumber": "10100000001",
      "accountName": "John Doe",
      "product": "FD",
      "scheme": "REGULAR",
      "depositAmount": 75000.0,
      "depositMonths": 12,
      "depositDays": 0,
      "intRate": 7.5,
      "openDate": "2025-01-15T00:00:00.000+00:00",
      "maturityDate": "2026-01-15T00:00:00.000+00:00",
      "depositStatus": 3,
      "customerId": 100001
    },
    "renewalAmount": 75000.0,
    "payoutAmount": 32500.0,
    "newMaturityDate": "2026-01-15T00:00:00.000+00:00"
  },
  "errorCode": "0",
  "errorMessage": null,
  "successMessage": "Partial renewal completed successfully",
  "successCode": "200 OK"
}
```

| Field          | Type             | Description                                  |
|----------------|------------------|----------------------------------------------|
| renewedAccount | DepositAccountDto| Updated deposit account details              |
| renewalAmount  | Double           | Amount renewed into the new deposit          |
| payoutAmount   | Double           | Amount paid out to the customer              |
| newMaturityDate| Date             | New maturity date of the renewed deposit     |

### Error Responses

| Status | Condition                                  | Message                                              |
|--------|--------------------------------------------|------------------------------------------------------|
| 400    | Account not found                          | Deposit Account Not Found                            |
| 400    | Account not live (status != 3)             | Deposit Account not Live :- {accountNumber}          |
| 400    | Renewal amount missing or <= 0             | Renewal amount is required and must be greater than 0|
| 400    | Renewal amount > maturity amount           | Renewal amount cannot exceed maturity amount: {amt}  |

---

## Common Error Response Format

All error responses follow this structure:

```json
{
  "timestamp": "2025-01-15T10:30:00",
  "status": 400,
  "message": "Error description",
  "path": "uri=/api/v1/activity/closure"
}
```

## Deposit Status Codes

| Code | Status      |
|------|-------------|
| 1    | Pending     |
| 3    | Live/Active |
| 5    | Closed      |

## Auth Status Codes

| Code | Status   |
|------|----------|
| P    | Pending  |
| A    | Approved |
| R    | Rejected |
