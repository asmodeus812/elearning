### 1. POST with @RequestBody
POST http://localhost:8080/products-body
Content-Type: application/json

{
    "id": "laptop",
    "name": "Laptop",
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

# -----------------------------------------------------------------------------
# Variables
# -----------------------------------------------------------------------------
@base_url = http://localhost:8080
@content_type = application/json

# -----------------------------------------------------------------------------
# GET / (get all videos)
# -----------------------------------------------------------------------------
GET {{base_url}}/
HTTP 200

# -----------------------------------------------------------------------------
# POST /create  (create a new video)
# -----------------------------------------------------------------------------
POST {{base_url}}/create
Content-Type: {{content_type}}

{
  "name": "Created from Hurl"
}

HTTP 200

# -----------------------------------------------------------------------------
# PUT /update/{id}  (update video name)
# -----------------------------------------------------------------------------
PUT {{base_url}}/update/1
Content-Type: {{content_type}}

{
  "name": "Updated from Hurl"
}

HTTP 200

# -----------------------------------------------------------------------------
# DELETE /delete/{id}  (delete video)
# -----------------------------------------------------------------------------
DELETE {{base_url}}/delete/1
HTTP 200
