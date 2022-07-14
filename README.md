# CrowdFunding-api-v1.0
[![Java](https://img.shields.io/badge/Java-1.8.0-red.svg?style=plastic)](https://www.oracle.com/java/technologies/)
[![Maven](https://img.shields.io/badge/Maven-3.6.0-purple.svg?style=plastic)](https://maven.apache.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.1.9.RELEASE-green.svg?style=plastic)](https://spring.io/projects/spring-boot)

1. [User Add](#1-user-add)
1. [User List](#2-user-list)

## Endpoints

### 1. User Add

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:1989/api/v1/funding/users
```

***Body:***

```js        
{
    "name": "Teguh Sudibyantoro",
    "dateOfBirth": "10/02/1991",
    "fullAdress": "Jalan Pemekaran No 99"
}
```

***Response:***

```js      
{
    "success": true,
    "errors": [],
    "requestId": "f2f419b7-deca-4f14-a0ad-33956eb9cd78",
    "timestamp": "2022-07-14 12:47:10 UTC"
}
```

### 2. User List

***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:1989/api/v1/funding/users
```

***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| page | 1 | Page No | 
| pageSize | 10 | Data per Page |
| name | Wawan | User name |

***Response:***

```js
{
    "success": true,
    "pagedata": {
        "totalCount": 3,
        "currentPage": 1,
        "pageCount": 1,
        "pageSize": 10
    },
    "data": [
        {
            "id": 13,
            "name": "Joko Widodo",
            "dateOfBirth": "10/03/1992",
            "fullAdress": "Dusun Pisang Rt 10 Rw 20"
        },
        {
            "id": 2,
            "name": "Teguh Sudibyantoro",
            "dateOfBirth": "10/02/1991",
            "fullAdress": "Jalan Pemekaran No 99"
        },
        {
            "id": 1,
            "name": "Wawan Setiawan",
            "dateOfBirth": "10/01/1990",
            "fullAdress": "Kompleks Asia Serasi No 100"
        }
    ],
    "errors": [],
    "requestId": "cbade3c0-44c9-4a4a-9d48-f0552a1693cc",
    "timestamp": "2022-07-14 14:10:45 UTC"
}
```
---
[Back to top](#crowdfunding-api-v10)
