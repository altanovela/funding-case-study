# CrowdFunding-api-v1.0 (backoffice)
[![Java](https://img.shields.io/badge/Java-1.8.0-red.svg?style=plastic)](https://www.oracle.com/java/technologies/)
[![Maven](https://img.shields.io/badge/Maven-3.6.0-purple.svg?style=plastic)](https://maven.apache.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.1.9.RELEASE-green.svg?style=plastic)](https://spring.io/projects/spring-boot)
[![Postgresql](https://img.shields.io/badge/PostgreSQL-13.1-9cf.svg?style=plastic)](https://www.postgresql.org/docs/13/release-13-1.html)

Hi this is Crowd Funding simulation application, it use Tenant as Joint Account which record Balance Summary. The Funding Activities scope is within a Tenant, so please create New Tenant before doing Transaction (Donate or Withdraw).

1. [Add Tenant (Joint Accounts)](#1-add-tenant)
1. [Search Tenant](#2-search-tenant)
1. [Add User](#3-add-user)
1. [Search User](#4-search-user)
1. [Add Funding Activity](#5-add-activity)
1. [Search Funding Activities](#6-search-activities)

---
## Endpoints

### 1. Add Tenant

***Endpoint:***

```bash
Method: POST
Type: application/json
URL: http://localhost:2989/api/v1/funding/tenants
```

***Body:***

```js        
{
    "name": "Desa Purwasari",
    "initialBalance": 0
}
```

***Response:***

```js        
{
    "success": true,
    "errors": [],
    "requestId": "3f49154f-c606-4ba5-a3fd-91f393247e5f",
    "timestamp": "2022-07-15 11:59:06 UTC"
}
```

### 2. Search Tenant

***Endpoint:***

```bash
Method: GET
URL: http://localhost:2989/api/v1/funding/tenants
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| page | 1 | Page No | 
| pageSize | 10 | Data per Page |
| name | Purwasari | Tenant Name |

***Response:***

```js        
{
    "success": true,
    "pagedata": {
        "totalCount": 1,
        "currentPage": 1,
        "pageCount": 1,
        "pageSize": 10
    },
    "data": [
        {
            "id": 3,
            "name": "Desa Purwasari",
            "fundingBalance": 0.00
        }
    ],
    "errors": [],
    "requestId": "822bee67-2cf4-4291-bb47-bede8be68f80",
    "timestamp": "2022-07-15 11:59:25 UTC"
}
```

### 3. Add User

***Endpoint:***

```bash
Method: POST
Type: application/json
URL: http://localhost:2989/api/v1/funding/users
```

***Body:***

```js        
{
    "name": "Wawan Setiawan",
    "dateOfBirth": "10/01/1990",
    "fullAdress": "Kompleks Asia Serasi No 100"
}
```

***Response:***

```js        
{
    "success": true,
    "errors": [],
    "requestId": "d4b7e5e0-2038-4ea4-aad1-730c25b74de3",
    "timestamp": "2022-07-15 12:01:11 UTC"
}
```

### 4. Search User

***Endpoint:***

```bash
Method: GET
URL: http://localhost:2989/api/v1/funding/users
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| page | 1 | Page No | 
| pageSize | 10 | Data per Page |
| name | Wawan | User Name |

***Response:***

```js        
{
    "success": true,
    "pagedata": {
        "totalCount": 1,
        "currentPage": 1,
        "pageCount": 1,
        "pageSize": 10
    },
    "data": [
        {
            "id": 1,
            "name": "Wawan Setiawan",
            "dateOfBirth": "10/01/1990",
            "fullAdress": "Kompleks Asia Serasi No 100"
        }
    ],
    "errors": [],
    "requestId": "fe635c78-373f-4e39-a309-4d1fb16f54bc",
    "timestamp": "2022-07-15 12:01:23 UTC"
}
```

### 5. Add Activity

***Endpoint:***

```bash
Method: POST
Type: application/json
URL: http://localhost:2989/api/v1/funding/activities
```

***Body:***

```js        
{
    "tenantId": 3,
    "userId": 1,
    "amount": "1000000",
    "type": "DONATE"
}
```

***Response:***

```js        
{
    "success": true,
    "errors": [],
    "requestId": "8c6ff91c-6844-4b0c-99f1-d0e9fef09ce7",
    "timestamp": "2022-07-15 12:04:13 UTC"
}
```

### 6. Search Activities

***Endpoint:***

```bash
Method: GET
URL: http://localhost:2989/api/v1/funding/activities
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| page | 1 | Page No | 
| pageSize | 10 | Data per Page |
| tenantId | 3 | Numeric Tenant Id |
| userId | 1 | Numeric User Id |
| startDate | 15/07/2022 | Transaction Date, format : dd/MM/yyyy |
| endDate | 15/07/2022 | Transaction Date, format : dd/MM/yyyy |

***Response:***

```js        
{
    "success": true,
    "pagedata": {
        "totalCount": 1,
        "currentPage": 1,
        "pageCount": 1,
        "pageSize": 10
    },
    "data": [
        {
            "tenantName": "Desa Purwasari",
            "userName": "Wawan Setiawan",
            "type": "DONATE",
            "amount": 1000000.00,
            "transactionTime": "2022-07-15 12:04:13 UTC"
        }
    ],
    "errors": [],
    "requestId": "c86fa3c4-3e13-4781-98f7-a9e5efba8a0b",
    "timestamp": "2022-07-15 12:04:31 UTC"
}
```

---

## Entity Diagram
<img src="https://raw.githubusercontent.com/altanovela/funding-case-study/main/.res/table_diagram.png" width="60%"/>

[Back to top](#crowdfunding-api-v10)

