# User API Specs

## Register User

### Endpoint: POST /api/users

Request Body
```json
{
  "username": "user-arh",
  "pasword": "secret",
  "name": "Arif"
}
```

Response Body (Success)
```json
{
  "success": true,
  "data": "OK",
  "errors": ""
}
```

Response Body (Failed)
```json
{
  "success": false,
  "data": null,
  "errors": "generic error" 
}
```

## Login User

### Endpoint: POST /api/auth/login

Request Body
```json
{
  "username": "user-arh",
  "pasword": "secret"
}
```

Response Body (Success)
```json
{
  "success": true,
  "data": {
    "token": "some-token",
    "expiredAt": "23454332" // milisecond
  },
  "error": ""
}
```

Response Body (Failed, 401)
```json
{
  "success": false,
  "data": null,
  "error": "username or password not correct"
}
```

## Get Current User

### Endpoint: POST /api/user/current

Request Header
```text
X-API-TOKEN: <TOKEN IS MANDATORY>
```

Response Body (Success)
```json
{
  "success": true,
  "data": {
    "username": "user-arh",
    "name": "Arif"
  },
  "error": ""
}
```

Response Body (Failed, 401)
```json
{
  "success": false,
  "data": null,
  "error": "unauthorized"
}
```

## Update User

### Endpoint: PATCH /api/user/{username}

Request Header
```text
X-API-TOKEN: <TOKEN IS MANDATORY>
```

Request Body
```json
{
  "name": "Arif RH"
}
```

Response Body (Success)
```json
{
  "success": true,
  "data": {
    "username": "user-arh",
    "name": "Arif RH"
  },
  "error": ""
}
```

Response Body (Failed, 401)
```json
{
  "success": false,
  "data": null,
  "error": "username or password not correct"
}
```

## Logout user

### Endpoint: DELETE /api/auth/logout

Request Header
```text
X-API-TOKEN: <TOKEN IS MANDATORY>
```

Response Body (Success)
```json
{
  "success": true,
  "data": "OK",
  "errors": ""
}
```







