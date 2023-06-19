# REST API example application

The REST API to the example app is described below.

First, you must register a new user. Only registered users can work with the api. Authorization works with a JWT token.

## Registration a new User

POST

    http://localhost:8080/online-shop/api/v1/auth/register

Request:

Body:
    
    {
        "username":"artur@yopmail.com",
        "password":"123"
    }

Response:

    {

        "message": "Registration is successful"

    }

Status 201: Created


## Login in system

POST

    http://localhost:8080/online-shop/api/v1/auth/login

Request:

Body:

    {
        "username":"artur@yopmail.com",
        "password":"123"
    }

Response:

    {

        "username":"artur@yopmail.com",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcnR1cjFAZ21haWwuY29tIiwiZXhwIjoxNjQwNjM4ODAwfQ.
            SY8cEb2Fo2aMqRUDmo3KKYZRVlu-p-LwCWbcx0umZmvAE_eqwn4FFTaoPLJzgG85WzUnz8_nzDoYIlbskxKzKA"

    }


## Authorization(All endpoints except **/login and **/register work only if there is JWT token)

Authorization : Bearer token

Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcnR1cjFAZ21haWwuY29tIiwiZXhwIjoxNjQwNjM4ODAwfQ.
SY8cEb2Fo2aMqRUDmo3KKYZRVlu-p-LwCWbcx0umZmvAE_eqwn4FFTaoPLJzgG85WzUnz8_nzDoYIlbskxKzKA


## Get user by user ID

GET

    http://localhost:8080/online-shop/api/v1/users/{id}

## Get list of all users

GET

    http://localhost:8080/online-shop/api/v1/users

## Get list of user's(userId) orders

GET

    http://localhost:8080/online-shop/api/v1/users/{userId}/orders

## Get good by good ID

GET

    http://localhost:8080/online-shop/api/v1/goods/{id}

## Get list of all goods

GET

    http://localhost:8080/online-shop/api/v1/goods

 ## Get order by order ID

GET

    http://localhost:8080/online-shop/api/v1/orders/{id}

## Get list of all orders

GET

    http://localhost:8080/online-shop/api/v1/goods


## Create a new Order

POST

    http://localhost:8080/online-shop/api/v1/orders

Request:

Body: 

{
    
    "userId": 1,
    "orderedGoods": [
        {
            "id": 2,
            "title": "Pencil",
            "price": 1.00
        },
        {
            "id": 2,
            "title": "Pencil",
            "price": 1.00
        }
    ]
}
    
Response:

{
    "id": 1,
    "userId": 1,
    "totalPrice": 2.00,
    "orderedGoods": [
        {
            "id": 2,
            "title": "Pencil",
            "price": 1.00
        },
        {
            "id": 2,
            "title": "Pencil",
            "price": 1.00
        }
    ]
}     

## Update order (add Good to Order)

PUT

    http://localhost:8080/online-shop/api/v1/orders/{orderId}/goods/{goodId}"

Request:

Body: none or raw {}
    
Response:

Body: none

Status 204: No content  (good save in order)

## Receive last order to user's email

Credentials for email sender:

mail.username = artasiptsou@gmail.com

mail.password = 1a2b3c4D5E 

Credentials for email recipient:

mail.username = artur@yopmail.com

no password needed

POST

    http://localhost:8080/online-shop/api/v1/orders/emails

Request:

Body: none or raw{}

Response:

Status 201: Created

## Upload file in order(it make attachment in order)

Max. size of uploaded file 250kB and valid extension is jpg, jpeg, bmp, png

POST

    http://localhost:8080/online-shop/api/v1/orders/{orderId}/file

Request:

Body: form-data

key  : file    value :  {your image}

Response:

Status 200: Ok  

## Download attachment from order(if it exist)

GET

    http://localhost:8080/online-shop/api/v1/orders/{orderId}/file


Response:

{your image}

Status 200: Ok  
