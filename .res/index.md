
# CrowdFunding-api-v1.0



<!--- If we have only one group/collection, then no need for the "ungrouped" heading -->
1. [Search Activities](#1-search-activities)
1. [Add Activity](#2-add-activity)
1. [Search User](#3-search-user)
1. [Add User](#4-add-user)
1. [Search Tenant](#5-search-tenant)
1. [Add Tenant](#6-add-tenant)



## Endpoints


--------



### 1. Search Activities



***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:2989/api/v1/funding/activities
```



### 2. Add Activity



***Endpoint:***

```bash
Method: POST
Type: RAW
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



### 3. Search User



***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:2989/api/v1/funding/users
```



### 4. Add User



***Endpoint:***

```bash
Method: POST
Type: RAW
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



### 5. Search Tenant



***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:2989/api/v1/funding/tenants
```



### 6. Add Tenant



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:2989/api/v1/funding/tenants
```



***Body:***

```js        
{
    "name": "Desa Purwasari",
    "initialBalance": 0
}
```



---
[Back to top](#crowdfunding-api-v10)

>Generated at 2022-07-15 19:07:49 by [docgen](https://github.com/thedevsaddam/docgen)
