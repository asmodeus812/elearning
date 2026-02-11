### 1. POST with @RequestBody
POST http://localhost:8080/products-body
Content-Type: application/json

{
    "id": "laptop",
    "name": "Laptop"
}

### 2. GET with @RequestHeader
GET http://localhost:8080/products-header
name: PremiumCustomer

### 3. GET with @RequestParam
GET http://localhost:8080/products-query?name=Smartphone

### 4. GET with @PathVariable
GET http://localhost:8080/products-path/12345

### 5. GET with @RequestAttribute (requires server-side setup)
GET http://localhost:8080/products-discount

GET http://localhost:8080/api/list?page=0&size=10
Authorization: Basic YWRtaW46YWRtaW4xMjM=

HTTP 200

POST http://localhost:8080/api/find
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json

{
  "name": "the"
}

HTTP 200

POST http://localhost:8080/api/create
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json

{
  "name": "Hurl video",
  "description": "Created from Hurl"
}

HTTP 200

PUT http://localhost:8080/api/update
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json

{
    "id": 1,
    "description": "Updated from Hurl"
}

HTTP 200

DELETE http://localhost:8080/api/delete/1
Authorization: Basic YWRtaW46YWRtaW4xMjM=
HTTP 200
